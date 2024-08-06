package com.fox.energy.sweep.controller;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import cn.hutool.core.bean.BeanUtil;
import com.fox.energy.sweep.domain.vo.TronSweepLogVo;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fox.energy.common.annotation.Log;
import com.fox.energy.common.core.controller.BaseController;
import com.fox.energy.common.core.domain.AjaxResult;
import com.fox.energy.common.enums.BusinessType;
import com.fox.energy.sweep.domain.TronSweepLog;
import com.fox.energy.sweep.service.ITronSweepLogService;
import com.fox.energy.common.utils.poi.ExcelUtil;
import com.fox.energy.common.core.page.TableDataInfo;

/**
 * 归集记录Controller
 *
 * @author IEME
 * @date 2024-05-16
 */
@RestController
@RequestMapping("/sweep/log")
public class TronSweepLogController extends BaseController {
    @Autowired
    private ITronSweepLogService tronSweepLogService;

    /**
     * 查询归集记录列表
     */
    @PreAuthorize("@ss.hasPermi('sweep:log:list')")
    @GetMapping("/list")
    public TableDataInfo list(TronSweepLog tronSweepLog) {
        startPage();
        List<TronSweepLog> list = tronSweepLogService.selectList(tronSweepLog);
        TableDataInfo dataTable = getDataTable(list);
        List<TronSweepLogVo> vos = new ArrayList<>();
        for (TronSweepLog sweepLog : list) {
            TronSweepLogVo sweepLogVo = BeanUtil.copyProperties(sweepLog, TronSweepLogVo.class);
            vos.add(sweepLogVo);
        }
        dataTable.setRows(vos);
        return dataTable;
    }

    /**
     * 导出归集记录列表
     */
    @PreAuthorize("@ss.hasPermi('sweep:log:export')")
    @Log(title = "归集记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TronSweepLog tronSweepLog) {
        List<TronSweepLog> list = tronSweepLogService.selectList(tronSweepLog);
        ExcelUtil<TronSweepLog> util = new ExcelUtil<TronSweepLog>(TronSweepLog.class);
        util.exportExcel(response, list, "归集记录数据");
    }

    /**
     * 获取归集记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('sweep:log:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(tronSweepLogService.selectById(id));
    }

    /**
     * 新增归集记录
     */
    @PreAuthorize("@ss.hasPermi('sweep:log:add')")
    @Log(title = "归集记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TronSweepLog tronSweepLog) {
        return toAjax(tronSweepLogService.insert(tronSweepLog));
    }

    /**
     * 修改归集记录
     */
    @PreAuthorize("@ss.hasPermi('sweep:log:edit')")
    @Log(title = "归集记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TronSweepLog tronSweepLog) {
        return toAjax(tronSweepLogService.update(tronSweepLog));
    }

    /**
     * 删除归集记录
     */
    @PreAuthorize("@ss.hasPermi('sweep:log:remove')")
    @Log(title = "归集记录", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(tronSweepLogService.deleteByIds(ids));
    }
}
