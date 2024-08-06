package com.fox.energy.web.admin.version;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

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
import com.fox.energy.app.domain.AppVersion;
import com.fox.energy.app.service.IAppVersionService;
import com.fox.energy.common.utils.poi.ExcelUtil;
import com.fox.energy.common.core.page.TableDataInfo;

/**
 * APP版本Controller
 *
 * @author IEME
 * @date 2024-05-27
 */
@RestController
@RequestMapping("/application/version")
public class AppVersionController extends BaseController {
    @Autowired
    private IAppVersionService appVersionService;

    /**
     * 查询APP版本列表
     */
    @PreAuthorize("@ss.hasPermi('app:version:list')")
    @GetMapping("/list")
    public TableDataInfo list(AppVersion appVersion) {
        startPage();
        List<AppVersion> list = appVersionService.selectList(appVersion);
        return getDataTable(list);
    }

    /**
     * 导出APP版本列表
     */
    @PreAuthorize("@ss.hasPermi('app:version:export')")
    @Log(title = "APP版本", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, AppVersion appVersion) {
        List<AppVersion> list = appVersionService.selectList(appVersion);
        ExcelUtil<AppVersion> util = new ExcelUtil<AppVersion>(AppVersion.class);
        util.exportExcel(response, list, "APP版本数据");
    }

    /**
     * 获取APP版本详细信息
     */
    @PreAuthorize("@ss.hasPermi('app:version:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id) {
        return success(appVersionService.selectById(id));
    }

    /**
     * 新增APP版本
     */
    @PreAuthorize("@ss.hasPermi('app:version:add')")
    @Log(title = "APP版本", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody AppVersion appVersion) {
        return toAjax(appVersionService.insert(appVersion));
    }

    /**
     * 修改APP版本
     */
    @PreAuthorize("@ss.hasPermi('app:version:edit')")
    @Log(title = "APP版本", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody AppVersion appVersion) {
        return toAjax(appVersionService.update(appVersion));
    }

    /**
     * 删除APP版本
     */
    @PreAuthorize("@ss.hasPermi('app:version:remove')")
    @Log(title = "APP版本", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids) {
        return toAjax(appVersionService.deleteByIds(ids));
    }
}
