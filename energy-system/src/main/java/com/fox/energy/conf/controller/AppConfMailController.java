package com.fox.energy.conf.controller;

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
import com.fox.energy.conf.domain.AppConfMail;
import com.fox.energy.conf.service.IAppConfMailService;
import com.fox.energy.common.utils.poi.ExcelUtil;
import com.fox.energy.common.core.page.TableDataInfo;

/**
 * 邮件配置Controller
 * 
 * @author IEME
 * @date 2024-07-22
 */
@RestController
@RequestMapping("/config/mail")
public class AppConfMailController extends BaseController
{
    @Autowired
    private IAppConfMailService appConfMailService;

    /**
     * 查询邮件配置列表
     */
    @PreAuthorize("@ss.hasPermi('config:mail:list')")
    @GetMapping("/list")
    public TableDataInfo list(AppConfMail appConfMail)
    {
        startPage();
        List<AppConfMail> list = appConfMailService.selectList(appConfMail);
        return getDataTable(list);
    }

    /**
     * 导出邮件配置列表
     */
    @PreAuthorize("@ss.hasPermi('config:mail:export')")
    @Log(title = "邮件配置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, AppConfMail appConfMail)
    {
        List<AppConfMail> list = appConfMailService.selectList(appConfMail);
        ExcelUtil<AppConfMail> util = new ExcelUtil<AppConfMail>(AppConfMail.class);
        util.exportExcel(response, list, "邮件配置数据");
    }

    /**
     * 获取邮件配置详细信息
     */
    @PreAuthorize("@ss.hasPermi('config:mail:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(appConfMailService.selectById(id));
    }

    /**
     * 新增邮件配置
     */
    @PreAuthorize("@ss.hasPermi('config:mail:add')")
    @Log(title = "邮件配置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody AppConfMail appConfMail)
    {
        return toAjax(appConfMailService.insert(appConfMail));
    }

    /**
     * 修改邮件配置
     */
    @PreAuthorize("@ss.hasPermi('config:mail:edit')")
    @Log(title = "邮件配置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody AppConfMail appConfMail)
    {
        return toAjax(appConfMailService.update(appConfMail));
    }

    /**
     * 删除邮件配置
     */
    @PreAuthorize("@ss.hasPermi('config:mail:remove')")
    @Log(title = "邮件配置", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(appConfMailService.deleteByIds(ids));
    }
}
