package com.fox.energy.sweep.task;

import com.fox.energy.common.constant.QueueConstant;
import com.fox.energy.framework.mq.JmsProducer;
import com.fox.energy.sweep.domain.TronSweepLog;
import com.fox.energy.sweep.service.ITronSweepLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 资金归集處理
 */
@Component("tronSweepHandleTask")
public class TronSweepHandleTask {

    @Autowired
    private JmsProducer jmsProducer;
    @Autowired
    private ITronSweepLogService tronSweepLogService;

    public void doTask() {
        //1未归集 2归集中 3归集成功 4归集失败
        TronSweepLog log = new TronSweepLog();
        log.setStatus(1);
        List<TronSweepLog> logs = tronSweepLogService.selectList(log);
        for (TronSweepLog sweepLog : logs) {
            //查询地址是否启用
            jmsProducer.send(QueueConstant.TRON_SWEEP_HANDLE, sweepLog.getSweepId());
        }
    }

}
