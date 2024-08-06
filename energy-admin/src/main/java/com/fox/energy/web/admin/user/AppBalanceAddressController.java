package com.fox.energy.web.admin.user;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.fox.energy.framework.web.domain.server.Sys;
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
import com.fox.energy.user.domain.AppBalanceAddress;
import com.fox.energy.user.service.IAppBalanceAddressService;
import com.fox.energy.common.utils.poi.ExcelUtil;
import com.fox.energy.common.core.page.TableDataInfo;

/**
 * 地址余额Controller
 *
 * @author IEME
 * @date 2024-05-14
 */
@RestController
@RequestMapping("/user/balance/address")
public class AppBalanceAddressController extends BaseController {
    @Autowired
    private IAppBalanceAddressService appBalanceAddressService;

    /**
     * 查询地址余额列表
     */
    @PreAuthorize("@ss.hasPermi('user:balanceAddress:list')")
    @GetMapping("/list")
    public TableDataInfo list(AppBalanceAddress appBalanceAddress) {
        startPage();
        List<AppBalanceAddress> list = appBalanceAddressService.selectList(appBalanceAddress);
        return getDataTable(list);
    }

    /**
     * 导出地址余额列表
     */
    @PreAuthorize("@ss.hasPermi('user:balanceAddress:export')")
    @Log(title = "地址余额", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, AppBalanceAddress appBalanceAddress) {
        List<AppBalanceAddress> list = appBalanceAddressService.selectList(appBalanceAddress);
        ExcelUtil<AppBalanceAddress> util = new ExcelUtil<AppBalanceAddress>(AppBalanceAddress.class);
        util.exportExcel(response, list, "地址余额数据");
    }

    /**
     * 获取地址余额详细信息
     */
    @PreAuthorize("@ss.hasPermi('user:balanceAddress:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(appBalanceAddressService.selectById(id));
    }

    /**
     * 新增地址余额
     */
    @PreAuthorize("@ss.hasPermi('user:balanceAddress:add')")
    @Log(title = "地址余额", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody AppBalanceAddress appBalanceAddress) {
        return toAjax(appBalanceAddressService.insert(appBalanceAddress));
    }

    /**
     * 修改地址余额
     */
    @PreAuthorize("@ss.hasPermi('user:balanceAddress:edit')")
    @Log(title = "地址余额", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody AppBalanceAddress appBalanceAddress) {
        AppBalanceAddress address = appBalanceAddressService.selectById(appBalanceAddress.getId());
        appBalanceAddressService.changeBalance(appBalanceAddress.getAddress(), "人工操作_" + System.currentTimeMillis(), appBalanceAddress.getBalance().subtract(address.getBalance()));
        return success();
    }

    /**
     * 删除地址余额
     */
    @PreAuthorize("@ss.hasPermi('user:balanceAddress:remove')")
    @Log(title = "地址余额", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(appBalanceAddressService.deleteByIds(ids));
    }
}
