package com.fox.energy.sweep.controller;

import cn.hutool.core.date.DateUtil;
import com.fox.energy.common.annotation.Log;
import com.fox.energy.common.constant.QueueConstant;
import com.fox.energy.common.core.controller.BaseController;
import com.fox.energy.common.core.domain.AjaxResult;
import com.fox.energy.common.core.page.TableDataInfo;
import com.fox.energy.common.enums.BusinessType;
import com.fox.energy.common.utils.uuid.IdUtils;
import com.fox.energy.framework.mq.JmsProducer;
import com.fox.energy.sweep.domain.TronSweep;
import com.fox.energy.sweep.enums.SweepType;
import com.fox.energy.sweep.service.ITronSweepService;
import com.fox.energy.sweep.service.TronSweepServer;
import com.fox.energy.tron.domain.TronAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 归集批次Controller
 *
 * @author IEME
 * @date 2024-05-16
 */
@RestController
@RequestMapping("/sweep/batch")
public class TronSweepController extends BaseController {
    @Autowired
    private ITronSweepService tronSweepService;

    @Autowired
    private JmsProducer jmsProducer;

    @Autowired
    private TronSweepServer tronSweepServer;

    /**
     * 查询归集批次列表
     */
    @PreAuthorize("@ss.hasPermi('sweep:batch:list')")
    @GetMapping("/list")
    public TableDataInfo list(TronSweep tronSweep) {
        startPage();
        List<TronSweep> list = tronSweepService.selectList(tronSweep);
        return getDataTable(list);
    }

    /**
     * 新增归集批次
     */
    @PreAuthorize("@ss.hasPermi('sweep:batch:add')")
    @Log(title = "归集批次", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TronSweep tronSweep) {
        TronSweep sweep = new TronSweep();
        sweep.setType(tronSweep.getType());
        sweep.setStatus(1);
        List<TronSweep> tronSweeps = tronSweepService.selectList(sweep);
        if (!tronSweeps.isEmpty()) {
            return AjaxResult.error("当前类型已有任务在执行中");
        }
        sweep.setStatus(2);
        List<TronSweep> sweeps = tronSweepService.selectList(sweep);
        if (!sweeps.isEmpty()) {
            return AjaxResult.error("当前类型已有任务在执行中");
        }
        tronSweep.setSweepNo(DateUtil.format(DateUtil.date(), "yyyyMMdd-HHmm-") + IdUtils.fastSimpleUUID().substring(0, 6));
        tronSweep.setTitle("归集任务 " + DateUtil.now());
        tronSweep.setStatus(1);
        return toAjax(tronSweepService.insert(tronSweep));
    }

    /**
     * 导入批次
     */
    @PreAuthorize("@ss.hasPermi('sweep:batch:add')")
    @Log(title = "归集批次", businessType = BusinessType.INSERT)
    @PostMapping("/import/{id}")
    public AjaxResult importAddress(@PathVariable Long id) {
        TronSweep tronSweep = tronSweepService.selectById(id);
        if (tronSweep == null) {
            return AjaxResult.error("任务不存在");
        }
        if (tronSweep.getStatus() != 1) {
            return AjaxResult.error("任务已导入");
        }

        jmsProducer.send(QueueConstant.TRON_SWEEP_ADDRESS_IMPORT, tronSweep.getId());
        return AjaxResult.success();
    }

    /**
     * 地址归集
     */
    @Log(title = "TRON地址-归集", businessType = BusinessType.OTHER)
    @PostMapping("/sweep")
    public AjaxResult sweep(@RequestBody TronAddress address) {
        return result(tronSweepServer.sweep(address.getAddress(), SweepType.getType(address.getType())));
    }
}
