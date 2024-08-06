package com.fox.energy.sweep.task;

import com.fox.energy.common.core.domain.tron.TronTransactionModel;
import com.fox.energy.tron.util.TronSupport;
import com.fox.energy.sweep.domain.TronSweepLog;
import com.fox.energy.sweep.service.ITronSweepLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 资金归集状态更新
 */
@Component("tronSweepLogStatusTask")
public class TronSweepLogStatusTask {

    @Autowired
    private ITronSweepLogService tronSweepLogService;

    public void doTask() {
        //1未归集 2归集中 3归集成功 4归集失败
        TronSweepLog log = new TronSweepLog();
        log.setStatus(2);
        List<TronSweepLog> logs = tronSweepLogService.selectList(log);
        for (TronSweepLog sweepLog : logs) {
            try {
                //查询地址是否启用
                TronTransactionModel model = TronSupport.getTransaction(sweepLog.getHash());
                if (model == null) {
                    continue;
                }
                if (!model.isContractRet()) {
                    TronSweepLog logUp = new TronSweepLog();
                    logUp.setId(sweepLog.getId());
                    logUp.setStatus(4);
                    logUp.setMessage("失败");
                    tronSweepLogService.update(logUp);
                } else if (model.isContractRet() && model.isConfirmed() && model.getConfirmations() >= 12) {
                    TronSweepLog logUp = new TronSweepLog();
                    logUp.setId(sweepLog.getId());
                    logUp.setStatus(3);
                    logUp.setMessage("成功");
                    tronSweepLogService.update(logUp);
                }
            } catch (Exception ignored) {

            }
        }
    }

}
