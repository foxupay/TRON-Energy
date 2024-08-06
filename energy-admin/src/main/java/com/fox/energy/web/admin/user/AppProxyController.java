package com.fox.energy.web.admin.user;

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
import com.fox.energy.user.domain.AppProxy;
import com.fox.energy.user.service.IAppProxyService;
import com.fox.energy.common.utils.poi.ExcelUtil;
import com.fox.energy.common.core.page.TableDataInfo;

/**
 * 代理利率Controller
 *
 * @author IEME
 * @date 2024-07-15
 */
@RestController
@RequestMapping("/user/proxy")
public class AppProxyController extends BaseController {
    @Autowired
    private IAppProxyService appProxyService;

    /**
     * 查询代理利率列表
     */
    @PreAuthorize("@ss.hasPermi('user:proxy:list')")
    @GetMapping("/list")
    public TableDataInfo list(AppProxy appProxy) {
        startPage();
        List<AppProxy> list = appProxyService.selectList(appProxy);
        return getDataTable(list);
    }

    /**
     * 导出代理利率列表
     */
    @PreAuthorize("@ss.hasPermi('user:proxy:export')")
    @Log(title = "代理利率", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, AppProxy appProxy) {
        List<AppProxy> list = appProxyService.selectList(appProxy);
        ExcelUtil<AppProxy> util = new ExcelUtil<AppProxy>(AppProxy.class);
        util.exportExcel(response, list, "代理利率数据");
    }

    /**
     * 获取代理利率详细信息
     */
    @PreAuthorize("@ss.hasPermi('user:proxy:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id) {
        return success(appProxyService.selectById(id));
    }

    /**
     * 新增代理利率
     */
    @PreAuthorize("@ss.hasPermi('user:proxy:add')")
    @Log(title = "代理利率", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody AppProxy appProxy) {
        return toAjax(appProxyService.insert(appProxy));
    }

    /**
     * 修改代理利率
     */
    @PreAuthorize("@ss.hasPermi('user:proxy:edit')")
    @Log(title = "代理利率", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody AppProxy appProxy) {
        return toAjax(appProxyService.update(appProxy));
    }

    /**
     * 删除代理利率
     */
    @PreAuthorize("@ss.hasPermi('user:proxy:remove')")
    @Log(title = "代理利率", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids) {
        return toAjax(appProxyService.deleteByIds(ids));
    }
}
