package com.fox.energy.app.controller;

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
import com.fox.energy.app.domain.AppApiKey;
import com.fox.energy.app.service.IAppApiKeyService;
import com.fox.energy.common.utils.poi.ExcelUtil;
import com.fox.energy.common.core.page.TableDataInfo;

/**
 * 接口秘钥Controller
 * 
 * @author IEME
 * @date 2024-07-24
 */
@RestController
@RequestMapping("/app/key")
public class AppApiKeyController extends BaseController
{
    @Autowired
    private IAppApiKeyService appApiKeyService;

    /**
     * 查询接口秘钥列表
     */
    @PreAuthorize("@ss.hasPermi('app:key:list')")
    @GetMapping("/list")
    public TableDataInfo list(AppApiKey appApiKey)
    {
        startPage();
        List<AppApiKey> list = appApiKeyService.selectList(appApiKey);
        return getDataTable(list);
    }

    /**
     * 导出接口秘钥列表
     */
    @PreAuthorize("@ss.hasPermi('app:key:export')")
    @Log(title = "接口秘钥", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, AppApiKey appApiKey)
    {
        List<AppApiKey> list = appApiKeyService.selectList(appApiKey);
        ExcelUtil<AppApiKey> util = new ExcelUtil<AppApiKey>(AppApiKey.class);
        util.exportExcel(response, list, "接口秘钥数据");
    }

    /**
     * 获取接口秘钥详细信息
     */
    @PreAuthorize("@ss.hasPermi('app:key:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id)
    {
        return success(appApiKeyService.selectById(id));
    }

    /**
     * 新增接口秘钥
     */
    @PreAuthorize("@ss.hasPermi('app:key:add')")
    @Log(title = "接口秘钥", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody AppApiKey appApiKey)
    {
        return toAjax(appApiKeyService.insert(appApiKey));
    }

    /**
     * 修改接口秘钥
     */
    @PreAuthorize("@ss.hasPermi('app:key:edit')")
    @Log(title = "接口秘钥", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody AppApiKey appApiKey)
    {
        return toAjax(appApiKeyService.update(appApiKey));
    }

    /**
     * 删除接口秘钥
     */
    @PreAuthorize("@ss.hasPermi('app:key:remove')")
    @Log(title = "接口秘钥", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids)
    {
        return toAjax(appApiKeyService.deleteByIds(ids));
    }
}
