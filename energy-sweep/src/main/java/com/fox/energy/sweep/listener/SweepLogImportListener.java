package com.fox.energy.sweep.listener;

import cn.hutool.core.date.DateUtil;
import com.fox.energy.common.constant.QueueConstant;
import com.fox.energy.common.utils.uuid.IdUtils;
import com.fox.energy.framework.mq.JmsProducer;
import com.fox.energy.framework.mq.RabbitConstants;
import com.fox.energy.sweep.domain.TronSweep;
import com.fox.energy.sweep.domain.TronSweepLog;
import com.fox.energy.sweep.service.ITronSweepLogService;
import com.fox.energy.sweep.service.ITronSweepService;
import com.fox.energy.tron.domain.TronAddress;
import com.fox.energy.tron.service.ITronAddressService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 导入归集地址
 */
@Component
public class SweepLogImportListener {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ITronSweepService tronSweepService;

    @Autowired
    private ITronAddressService tronAddressService;

    @Autowired
    private ITronSweepLogService tronSweepLogService;

    @Autowired
    private JmsProducer jmsProducer;

    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(name = RabbitConstants.QUEUE),
                    exchange = @Exchange(value = RabbitConstants.EXCHANGE),
                    key = QueueConstant.TRON_SWEEP_ADDRESS_IMPORT
            )
    )
    public void task(Long id) {
        TronSweep tronSweep = tronSweepService.selectById(id);
        if (tronSweep == null) {
            return;
        }
        if (tronSweep.getStatus() != 1) {
            return;
        }

        int sweepNumber = 0;
        List<TronSweepLog> logs = new ArrayList<>();
        List<TronAddress> addresses = tronAddressService.selectList(null);
        for (TronAddress address : addresses) {

            if (tronSweep.getType() == 1 && address.getAmtTrx().doubleValue() <= tronSweep.getMinAmount().doubleValue()) {
                continue;
            }
            if (tronSweep.getType() == 2 && address.getAmtUsdt().doubleValue() <= tronSweep.getMinAmount().doubleValue()) {
                continue;
            }
            //TRX为账户保留5TRX，用做手续费
            BigDecimal amount = tronSweep.getType() == 1 ? address.getAmtTrx().subtract(new BigDecimal("1")) : address.getAmtUsdt();

            if (amount.doubleValue() <= 0) {
                continue;
            }

            //地址校验,未启用，甩出启用，当前批次不参与
            if (address.getEnable() == 1) {
                jmsProducer.send(QueueConstant.TRON_ADDRESS_ACTIVATE, address.getAddress());
                continue;
            }

            //启用中，当前批次不参与
            if (address.getEnable() == 2) {
                continue;
            }

            TronSweepLog sweepLog = new TronSweepLog();
            sweepLog.setSweepNo(tronSweep.getSweepNo());
            sweepLog.setSweepId(IdUtils.orderNo());
            sweepLog.setAddress(address.getAddress());
            sweepLog.setType(tronSweep.getType());
            sweepLog.setBalance(address.getAmtTrx());
            sweepLog.setStatus(1);
            sweepLog.setMessage("未处理");
            sweepLog.setCreateTime(DateUtil.now());
            sweepLog.setUpdateTime(DateUtil.now());
            logs.add(sweepLog);
            if (logs.size() >= 50) {
                tronSweepLogService.insertBatch(logs);
                logs.clear();
            }
            sweepNumber++;
        }
        if (!logs.isEmpty()) {
            tronSweepLogService.insertBatch(logs);
        }

        TronSweep sweep = new TronSweep();
        sweep.setId(tronSweep.getId());
        sweep.setAddressNumber(addresses.size());
        sweep.setSweepNumber(sweepNumber);
        sweep.setSuccessNumber(0);
        if (sweepNumber == 0) {
            sweep.setStatus(4);
        } else {
            sweep.setStatus(3);
        }
        tronSweepService.update(sweep);
    }

}
