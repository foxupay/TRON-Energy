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
import com.fox.energy.conf.domain.AppChannel;
import com.fox.energy.conf.service.IAppChannelService;
import com.fox.energy.common.utils.poi.ExcelUtil;
import com.fox.energy.common.core.page.TableDataInfo;

/**
 * 能量通道Controller
 *
 * @author IEME
 * @date 2024-07-16
 */
@RestController
@RequestMapping("/channel/channel")
public class AppChannelController extends BaseController {
    @Autowired
    private IAppChannelService appChannelService;

    /**
     * 查询能量通道列表
     */
    @PreAuthorize("@ss.hasPermi('channel:channel:list')")
    @GetMapping("/list")
    public TableDataInfo list(AppChannel appChannel) {
        startPage();
        List<AppChannel> list = appChannelService.selectList(appChannel);
        return getDataTable(list);
    }

    /**
     * 导出能量通道列表
     */
    @PreAuthorize("@ss.hasPermi('channel:channel:export')")
    @Log(title = "能量通道", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, AppChannel appChannel) {
        List<AppChannel> list = appChannelService.selectList(appChannel);
        ExcelUtil<AppChannel> util = new ExcelUtil<AppChannel>(AppChannel.class);
        util.exportExcel(response, list, "能量通道数据");
    }

    /**
     * 获取能量通道详细信息
     */
    @PreAuthorize("@ss.hasPermi('channel:channel:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(appChannelService.selectById(id));
    }

    /**
     * 新增能量通道
     */
    @PreAuthorize("@ss.hasPermi('channel:channel:add')")
    @Log(title = "能量通道", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody AppChannel appChannel) {
        return toAjax(appChannelService.insert(appChannel));
    }

    /**
     * 修改能量通道
     */
    @PreAuthorize("@ss.hasPermi('channel:channel:edit')")
    @Log(title = "能量通道", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody AppChannel appChannel) {
        if (appChannel.getState() != null && appChannel.getState() == 1) {
            AppChannel channel = new AppChannel();
            channel.setState(2);
            appChannelService.update(channel);
        }
        return toAjax(appChannelService.update(appChannel));
    }

    /**
     * 删除能量通道
     */
    @PreAuthorize("@ss.hasPermi('channel:channel:remove')")
    @Log(title = "能量通道", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(appChannelService.deleteByIds(ids));
    }
}
