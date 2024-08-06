package com.fox.energy.lease.task;

import cn.hutool.core.date.DateUtil;
import com.fox.energy.common.constant.QueueConstant;
import com.fox.energy.framework.mq.JmsProducer;
import com.fox.energy.lease.channel.tool.ChannelPayEnums;
import com.fox.energy.lease.channel.tool.ChannelQueryResponse;
import com.fox.energy.lease.domain.AppLeaseTrade;
import com.fox.energy.lease.server.LeaseTradeServer;
import com.fox.energy.lease.service.IAppLeaseTradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 更新租赁状态
 */
@Component("leaseLeaseStatusTask")
public class LeaseLeaseStatusTask {

    @Autowired
    private IAppLeaseTradeService appLeaseTradeService;

    @Autowired
    private LeaseTradeServer leaseTradeServer;

    @Autowired
    private JmsProducer jmsProducer;

    //执行租赁
    public void lease() {
        AppLeaseTrade leaseTradeQuery = new AppLeaseTrade();
        leaseTradeQuery.setPayStatus(2);
        leaseTradeQuery.setLeaseStatus(1);
        List<AppLeaseTrade> trades = appLeaseTradeService.selectList(leaseTradeQuery);
        for (AppLeaseTrade leaseTrade : trades) {
            jmsProducer.send(QueueConstant.LEASE_DO, leaseTrade.getOrderNo());
        }
    }


    public void doTask() {
        AppLeaseTrade leaseTradeQuery = new AppLeaseTrade();
        leaseTradeQuery.setPayStatus(2);
        leaseTradeQuery.setLeaseStatus(2);
        List<AppLeaseTrade> trades = appLeaseTradeService.selectList(leaseTradeQuery);
        for (AppLeaseTrade leaseTrade : trades) {
            try {
                if (System.currentTimeMillis() - DateUtil.parse(leaseTrade.getLeaseTime()).getTime() < 10000L) {
                    break;
                }
                ChannelQueryResponse queryResponse = leaseTradeServer.query(leaseTrade);
                if (queryResponse.isSuccess() && queryResponse.getState().equals(ChannelPayEnums.SUCCESS)) {
                    AppLeaseTrade trade = new AppLeaseTrade();
                    trade.setId(leaseTrade.getId());
                    trade.setLeaseStatus(3);
                    trade.setFrozenTxId(queryResponse.getHash());
                    trade.setExpireTime(queryResponse.getRecoveryTime());
                    appLeaseTradeService.update(trade);
                    break;
                }
                if (queryResponse.isSuccess() && queryResponse.getState().equals(ChannelPayEnums.FAIL)) {
                    AppLeaseTrade trade = new AppLeaseTrade();
                    trade.setId(leaseTrade.getId());
                    trade.setLeaseStatus(4);
                    appLeaseTradeService.update(trade);
                    //失败，退款
                    leaseTradeServer.refund(leaseTrade.getOrderNo());
                    break;
                }
            } catch (Exception ignored) {

            }
        }
    }

}
