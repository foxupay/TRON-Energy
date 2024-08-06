package com.fox.energy.sweep.task;

import com.fox.energy.sweep.domain.TronSweep;
import com.fox.energy.sweep.service.ITronSweepService;
import com.fox.energy.sweep.service.TronSweepServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 归集任务状态更新
 */
@Component("tronSweepSyncTask")
public class TronSweepSyncTask {

    @Autowired
    private ITronSweepService tronSweepService;
    @Autowired
    private TronSweepServer tronSweepServer;

    public void doTask() {
        //归集状态 1未导入 2未处理 3处理中 4处理完成
        TronSweep sweepQuery = new TronSweep();
        sweepQuery.setStatus(3);
        List<TronSweep> sweeps = tronSweepService.selectList(sweepQuery);
        for (TronSweep sweep : sweeps) {
            try {
                tronSweepServer.sweepSync(sweep.getSweepNo());
            } catch (Exception ignored) {

            }
        }
    }

}
