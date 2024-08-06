package com.fox.energy.sweep.service;

import cn.hutool.core.date.DateUtil;
import com.fox.energy.common.constant.QueueConstant;
import com.fox.energy.common.core.domain.CommonResult;
import com.fox.energy.common.utils.uuid.IdUtils;
import com.fox.energy.framework.mq.JmsProducer;
import com.fox.energy.sweep.domain.TronSweep;
import com.fox.energy.sweep.domain.TronSweepLog;
import com.fox.energy.sweep.enums.SweepType;
import com.fox.energy.tron.domain.TronAddress;
import com.fox.energy.tron.service.ITronAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class TronSweepServer {

    @Autowired
    private ITronSweepService tronSweepService;

    @Autowired
    private ITronAddressService tronAddressService;

    @Autowired
    private ITronSweepLogService tronSweepLogService;

    @Autowired
    private JmsProducer jmsProducer;

    //地址归集
    public CommonResult sweep(String address, SweepType type) {

        if (type == null) {
            return CommonResult.error("不支持的代币类型");
        }

        TronAddress tronAddress = tronAddressService.selectByAddress(address);
        if (tronAddress == null) {
            return CommonResult.error("地址不存在");
        }

        TronSweep tronSweep = new TronSweep();
        tronSweep.setType(type.getType());
        tronSweep.setSweepNo(DateUtil.format(DateUtil.date(), "yyyyMMdd-HHmm-") + IdUtils.fastSimpleUUID().substring(0, 6));
        tronSweep.setTitle("单地址归集 " + DateUtil.now());

        if (tronSweep.getType() == 1 && tronAddress.getAmtTrx().doubleValue() <= 1) {
            return CommonResult.error("当前账号 TRX 余额不足");
        }
        if (tronSweep.getType() == 2 && tronAddress.getAmtUsdt().doubleValue() <= 0) {
            return CommonResult.error("当前账号 USDT 余额不足");
        }
        //TRX为账户保留5TRX，用做手续费
        BigDecimal amount = tronSweep.getType() == 1 ? tronAddress.getAmtTrx() : tronAddress.getAmtUsdt();

        //地址校验,未启用，甩出启用，当前批次不参与
        if (tronAddress.getEnable() == 1) {
            jmsProducer.send(QueueConstant.TRON_ADDRESS_ACTIVATE, tronAddress.getAddress());
            return CommonResult.error("当前账号暂未启用，正在启用中，请稍后再试");
        }

        //启用中，当前批次不参与
        if (tronAddress.getEnable() == 2) {
            return CommonResult.error("当前账号正在启用中，请稍后再试");
        }

        TronSweepLog sweepLog = new TronSweepLog();
        sweepLog.setSweepNo(tronSweep.getSweepNo());
        sweepLog.setSweepId(IdUtils.orderNo());
        sweepLog.setAddress(tronAddress.getAddress());
        sweepLog.setType(tronSweep.getType());
        sweepLog.setBalance(amount);
        sweepLog.setStatus(1);
        sweepLog.setMessage("未处理");
        sweepLog.setCreateTime(DateUtil.now());
        sweepLog.setUpdateTime(DateUtil.now());
        tronSweepLogService.insert(sweepLog);

        tronSweep.setAddressNumber(1);
        tronSweep.setSweepNumber(1);
        tronSweep.setSuccessNumber(0);
        tronSweep.setStatus(3);
        tronSweepService.insert(tronSweep);

        return CommonResult.success("任务已添加，请前往归集管理查询");
    }

    public void sweepSync(String sweepNo) {
        synchronized (sweepNo.intern()) {
            TronSweep tronSweep = tronSweepService.selectBySweepNo(sweepNo);
            if (tronSweep == null) {
                return;
            }
            if (tronSweep.getStatus() != 3) {
                return;
            }
            TronSweepLog sweepLog = new TronSweepLog();
            sweepLog.setSweepNo(tronSweep.getSweepNo());
            List<TronSweepLog> logs = tronSweepLogService.selectList(sweepLog);
            if (logs.isEmpty()) {
                return;
            }
            //获取logs中status=3的数量
            int count3 = (int) logs.stream().filter(log -> log.getStatus() == 3).count();
            int count2 = (int) logs.stream().filter(log -> log.getStatus() == 2).count();
            int count1 = (int) logs.stream().filter(log -> log.getStatus() == 1).count();
            TronSweep sweep = new TronSweep();
            sweep.setId(tronSweep.getId());
            sweep.setSuccessNumber(count3);
            if (count1 + count2 == 0) {
                sweep.setStatus(4);
            }
            tronSweepService.update(sweep);
        }
    }

}
