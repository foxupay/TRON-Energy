package com.fox.energy.lease.listener;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.fox.energy.common.constant.QueueConstant;
import com.fox.energy.common.core.domain.CommonResult;
import com.fox.energy.common.utils.Threads;
import com.fox.energy.framework.mq.RabbitConstants;
import com.fox.energy.lease.channel.tool.ChannelPayEnums;
import com.fox.energy.lease.channel.tool.ChannelPayResponse;
import com.fox.energy.lease.channel.tool.ChannelQueryResponse;
import com.fox.energy.lease.domain.AppLeaseTrade;
import com.fox.energy.lease.server.LeaseTradeServer;
import com.fox.energy.lease.service.IAppLeaseTradeService;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class LeaseListener {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private IAppLeaseTradeService appLeaseTradeService;

    @Autowired
    private LeaseTradeServer leaseTradeServer;


    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(name = RabbitConstants.QUEUE),
                    exchange = @Exchange(value = RabbitConstants.EXCHANGE),
                    key = QueueConstant.LEASE_DO
            )
    )
    public void task(String orderNo) {
        logger.info("执行租赁操作 {}", orderNo);
        AppLeaseTrade leaseTrade = appLeaseTradeService.selectByOrderNo(orderNo);
        if (leaseTrade == null) {
            logger.info("执行租赁操作 {}:订单不存在", orderNo);
            return;
        }
        //支付状态 1未支付 2支付成功 3支付取消 4已退款
        if (leaseTrade.getPayStatus() != 2) {
            logger.info("执行租赁操作 {}:订单未支付成功-{}", orderNo, leaseTrade.getPayStatus());
            return;
        }
        //租赁状态 1未租赁 2租赁中 3租赁成功 4租赁失败
        if (leaseTrade.getLeaseStatus() != 1) {
            logger.info("执行租赁操作 {}:订单已租赁过-{}", orderNo, leaseTrade.getLeaseStatus());
            return;
        }
        ChannelPayResponse leaseResult = leaseTradeServer.lease(leaseTrade);
        logger.info("执行租赁操作 {}:租赁返回 {}", orderNo, JSONUtil.toJsonPrettyStr(leaseResult));
        if (!leaseResult.isSuccess()) {
            AppLeaseTrade trade = new AppLeaseTrade();
            trade.setId(leaseTrade.getId());
            trade.setLeaseStatus(4);
            trade.setMessage(leaseResult.getMessage());
            appLeaseTradeService.update(trade);
            //退款
            leaseTradeServer.refund(leaseTrade.getOrderNo());
            return;
        }

        AppLeaseTrade trade = new AppLeaseTrade();
        trade.setId(leaseTrade.getId());
        trade.setLeaseNo(leaseResult.getOrderId());
        trade.setFrozenTxId(leaseResult.getHash());
        trade.setLeaseTime(DateUtil.now());
        trade.setLeaseStatus(2);
        appLeaseTradeService.update(trade);
        leaseTrade = appLeaseTradeService.selectByOrderNo(orderNo);
        long endTime = System.currentTimeMillis() + 60000;//60秒钟
        while (endTime > System.currentTimeMillis()) {
            Threads.sleep(1000);
            ChannelQueryResponse queryResponse = leaseTradeServer.query(leaseTrade);
            if (queryResponse.isSuccess() && queryResponse.getState().equals(ChannelPayEnums.SUCCESS)) {
                AppLeaseTrade tradeUp = new AppLeaseTrade();
                tradeUp.setId(leaseTrade.getId());
                tradeUp.setFrozenTxId(queryResponse.getHash());
                tradeUp.setLeaseStatus(3);
                tradeUp.setExpireTime(queryResponse.getRecoveryTime());
                appLeaseTradeService.update(tradeUp);
                break;
            }
        }
    }
}
