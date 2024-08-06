package com.fox.energy.web.admin.lease;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.github.pagehelper.PageHelper;
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
import com.fox.energy.lease.domain.AppLeaseTrade;
import com.fox.energy.lease.service.IAppLeaseTradeService;
import com.fox.energy.common.utils.poi.ExcelUtil;
import com.fox.energy.common.core.page.TableDataInfo;

/**
 * 能量租用订单Controller
 *
 * @author IEME
 * @date 2024-04-24
 */
@RestController
@RequestMapping("/lease/trade")
public class AppLeaseTradeController extends BaseController {
    @Autowired
    private IAppLeaseTradeService appLeaseTradeService;

    /**
     * 查询能量租用订单列表
     */
    @PreAuthorize("@ss.hasPermi('lease:trade:list')")
    @GetMapping("/list")
    public TableDataInfo list(AppLeaseTrade appLeaseTrade) {
        startPage();
        PageHelper.orderBy("id desc");
        List<AppLeaseTrade> list = appLeaseTradeService.selectList(appLeaseTrade);
        return getDataTable(list);
    }

    /**
     * 导出能量租用订单列表
     */
    @PreAuthorize("@ss.hasPermi('lease:trade:export')")
    @Log(title = "能量租用订单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, AppLeaseTrade appLeaseTrade) {
        List<AppLeaseTrade> list = appLeaseTradeService.selectList(appLeaseTrade);
        ExcelUtil<AppLeaseTrade> util = new ExcelUtil<AppLeaseTrade>(AppLeaseTrade.class);
        util.exportExcel(response, list, "能量租用订单数据");
    }

    /**
     * 获取能量租用订单详细信息
     */
    @PreAuthorize("@ss.hasPermi('lease:trade:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id) {
        return success(appLeaseTradeService.selectById(id));
    }

    /**
     * 新增能量租用订单
     */
    @PreAuthorize("@ss.hasPermi('lease:trade:add')")
    @Log(title = "能量租用订单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody AppLeaseTrade appLeaseTrade) {
        return toAjax(appLeaseTradeService.insert(appLeaseTrade));
    }

    /**
     * 修改能量租用订单
     */
    @PreAuthorize("@ss.hasPermi('lease:trade:edit')")
    @Log(title = "能量租用订单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody AppLeaseTrade appLeaseTrade) {
        return toAjax(appLeaseTradeService.update(appLeaseTrade));
    }

    /**
     * 删除能量租用订单
     */
    @PreAuthorize("@ss.hasPermi('lease:trade:remove')")
    @Log(title = "能量租用订单", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids) {
        return toAjax(appLeaseTradeService.deleteByIds(ids));
    }
}
