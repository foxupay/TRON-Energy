package com.fox.energy.sweep.listener;

import com.fox.energy.common.constant.QueueConstant;
import com.fox.energy.common.core.domain.CommonResult;
import com.fox.energy.common.core.domain.tron.TronAccountResponse;
import com.fox.energy.common.core.domain.tron.TronBalance;
import com.fox.energy.common.utils.Threads;
import com.fox.energy.framework.mq.RabbitConstants;
import com.fox.energy.lease.channel.tool.ChannelContext;
import com.fox.energy.lease.channel.tool.ChannelPayEnums;
import com.fox.energy.lease.channel.tool.ChannelPayResponse;
import com.fox.energy.lease.channel.tool.ChannelQueryResponse;
import com.fox.energy.sweep.domain.TronSweep;
import com.fox.energy.sweep.domain.TronSweepLog;
import com.fox.energy.sweep.service.ITronSweepLogService;
import com.fox.energy.sweep.service.ITronSweepService;
import com.fox.energy.sweep.service.TronSweepServer;
import com.fox.energy.system.service.ISysConfigService;
import com.fox.energy.tron.domain.TronAddress;
import com.fox.energy.tron.server.TronAddressServer;
import com.fox.energy.tron.service.ITronAddressService;
import com.fox.energy.tron.util.TronAmountUtil;
import com.fox.energy.tron.util.TronSupport;
import com.fox.energy.tron.util.TronTransferUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * 归集操作
 */
@Component
public class SweepHandleListener {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ISysConfigService sysConfigService;
    @Autowired
    private ITronSweepLogService tronSweepLogService;
    @Autowired
    private ITronSweepService tronSweepService;
    @Autowired
    private TronSweepServer tronSweepServer;
    @Autowired
    private TronAddressServer tronAddressServer;
    @Autowired
    private ITronAddressService tronAddressService;
    @Autowired
    private ChannelContext channelContext;


    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(name = RabbitConstants.QUEUE),
                    exchange = @Exchange(value = RabbitConstants.EXCHANGE),
                    key = QueueConstant.TRON_SWEEP_HANDLE
            )
    )
    public void task(String sweepId) {
        logger.info("归集操作 {}", sweepId);
        TronSweepLog sweepLog = tronSweepLogService.selectBySweepId(sweepId);
        if (sweepLog == null) {
            logger.info("归集操作 {} 记录不存在", sweepId);
            return;
        }
        TronSweep tronSweep = tronSweepService.selectBySweepNo(sweepLog.getSweepNo());
        if (tronSweep == null) {
            return;
        }
        // 归集状态 1未归集 2归集中 3归集成功 4归集失败
        if (sweepLog.getStatus() != 1) {
            logger.info("归集操作 {} 归集状态不可归集-{}", sweepId, sweepLog.getStatus());
            return;
        }

        TronAddress tronAddress = tronAddressService.selectByAddress(sweepLog.getAddress());
        if (tronAddress == null) {
            logger.info("归集操作 {} 地址不存在 {}", sweepId, sweepLog.getAddress());
            return;
        }

        //归集代币类型 1-TRX 2-USDT
        if (sweepLog.getType() == 1) {

            //查询余额
            TronBalance balance = TronSupport.getBalance(sweepLog.getAddress());
            if (balance.getTrx().doubleValue() - TronAmountUtil.up(BigDecimal.valueOf(tronSweep.getMinAmount())).doubleValue() <= 0) {
                sweepFail(sweepLog, "余额低于设置的最小额度：" + tronSweep.getMinAmount());
                return;
            }

            //保留1个TRX作为手续费
            BigDecimal amount = balance.getTrx().subtract(TronAmountUtil.up(new BigDecimal("1")));
            if (amount.doubleValue() <= 0) {
                sweepFail(sweepLog, "余额低于保留额度：1TRX(手续费保留)");
                return;
            }

            CommonResult commonResult = TronTransferUtil.transTRX(tronAddress.getPrivateKey(), sysConfigService.selectConfigByKey("app.tron.sweep.address"), amount);//预留5个作为燃烧费用
            if (!commonResult.isSuccess()) {
                sweepFail(sweepLog, "归集失败：" + commonResult.getMessage());
                return;
            }
            sweepSuccess(sweepLog, TronAmountUtil.down(amount), commonResult);
        }

        if (sweepLog.getType() == 2) {
            //查询余额
            TronBalance balance = TronSupport.getBalance(sweepLog.getAddress());
            if (balance.getUsdt().doubleValue() - TronAmountUtil.up(BigDecimal.valueOf(tronSweep.getMinAmount())).doubleValue() <= 0) {
                sweepFail(sweepLog, "余额低于设置的最小额度：" + tronSweep.getMinAmount());
                return;
            }
            String sweepAddress = sysConfigService.getRealByKey("app.tron.sweep.address");
            String sweepPrivate = sysConfigService.getRealByKey("app.tron.privateKey");
            //保证有2个TRX做手续费
            if (balance.getTrx().doubleValue() < 2) {
                TronTransferUtil.transTRX(sweepPrivate, sweepLog.getAddress(), TronAmountUtil.up(new BigDecimal("1")));
                Threads.sleep(1000);
                return;
            }

            TronAccountResponse account = TronSupport.getAccount(sweepLog.getAddress());
            if (account == null) {
                sweepFail(sweepLog, "归集失败：获取账户信息失败");
                return;
            }
            TronBalance sweepAddressBalance = TronSupport.getBalance(sweepAddress);
            long needEnergy = 32000;
            if (sweepAddressBalance.getUsdt().doubleValue() == 0) {
                needEnergy = 65000;
            }
            //判断现有能量是否足够
            if (account.getEnableEnergy() < needEnergy) {
                logger.info("归集操作 {} 租赁能量", sweepId);
                //租赁能量
                ChannelPayResponse payResponse = channelContext.getEnable().pay(channelContext.getEnableChannel(), sweepLog.getAddress(), 1, needEnergy);
                if (!payResponse.isSuccess() || !payResponse.getState().equals(ChannelPayEnums.SUCCESS)) {
                    return;
                }
                long endTime = System.currentTimeMillis() + 60000;//60秒钟
                boolean fail = true;
                while (endTime > System.currentTimeMillis()) {
                    Threads.sleep(1000);
                    ChannelQueryResponse queryResponse = channelContext.getEnable().query(channelContext.getEnableChannel(), payResponse.getOrderId());
                    if (queryResponse.isSuccess() && queryResponse.getState().equals(ChannelPayEnums.SUCCESS)) {
                        logger.info("归集操作 {} 租赁能量 {}", sweepId, queryResponse.getHash());
                        fail = false;
                        break;
                    }
                }
                if (fail) {
                    return;
                }
            }
            //转账归集
            CommonResult commonResult = TronTransferUtil.transUsdt(tronAddress.getPrivateKey(), sweepAddress, TronAmountUtil.down(balance.getUsdt()));
            if (!commonResult.isSuccess()) {
                sweepFail(sweepLog, "归集失败：" + commonResult.getMessage());
                return;
            }
            sweepSuccess(sweepLog, TronAmountUtil.down(balance.getUsdt()), commonResult);
        }
    }

    private void sweepSuccess(TronSweepLog sweepLog, BigDecimal balance, CommonResult commonResult) {
        TronSweepLog log = new TronSweepLog();
        log.setId(sweepLog.getId());
        log.setAmount(balance);
        log.setHash(commonResult.getData().getStr("hash"));
        log.setStatus(2);
        tronSweepLogService.update(log);
        tronSweepServer.sweepSync(sweepLog.getSweepNo());
        tronAddressServer.syncBalance(sweepLog.getAddress());
    }

    private void sweepFail(TronSweepLog sweepLog, String message) {
        TronSweepLog log = new TronSweepLog();
        log.setId(sweepLog.getId());
        log.setStatus(4);
        log.setMessage(message);
        tronSweepLogService.update(log);
        tronSweepServer.sweepSync(sweepLog.getSweepNo());
        tronAddressServer.syncBalance(sweepLog.getAddress());
    }

}
