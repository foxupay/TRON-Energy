package com.fox.energy.web.admin.user;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.fox.energy.user.domain.AppBalanceAddress;
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
import com.fox.energy.user.domain.AppBalance;
import com.fox.energy.user.service.IAppBalanceService;
import com.fox.energy.common.utils.poi.ExcelUtil;
import com.fox.energy.common.core.page.TableDataInfo;

/**
 * 用户余额Controller
 *
 * @author IEME
 * @date 2024-04-30
 */
@RestController
@RequestMapping("/user/balance")
public class AppBalanceController extends BaseController {
    @Autowired
    private IAppBalanceService appBalanceService;

    /**
     * 查询用户余额列表
     */
    @PreAuthorize("@ss.hasPermi('user:balance:list')")
    @GetMapping("/list")
    public TableDataInfo list(AppBalance appBalance) {
        startPage();
        List<AppBalance> list = appBalanceService.selectList(appBalance);
        return getDataTable(list);
    }

    /**
     * 导出用户余额列表
     */
    @PreAuthorize("@ss.hasPermi('user:balance:export')")
    @Log(title = "用户余额", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, AppBalance appBalance) {
        List<AppBalance> list = appBalanceService.selectList(appBalance);
        ExcelUtil<AppBalance> util = new ExcelUtil<AppBalance>(AppBalance.class);
        util.exportExcel(response, list, "用户余额数据");
    }

    /**
     * 获取用户余额详细信息
     */
    @PreAuthorize("@ss.hasPermi('user:balance:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(appBalanceService.selectById(id));
    }

    /**
     * 新增用户余额
     */
    @PreAuthorize("@ss.hasPermi('user:balance:add')")
    @Log(title = "用户余额", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody AppBalance appBalance) {
        return toAjax(appBalanceService.insert(appBalance));
    }

    /**
     * 修改用户余额
     */
    @PreAuthorize("@ss.hasPermi('user:balance:edit')")
    @Log(title = "用户余额", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody AppBalance appBalance) {
        AppBalance balance = null;
        if (appBalance.getUserId() != null) {
            balance = appBalanceService.selectByUserId(appBalance.getUserId());
            appBalance.setId(balance.getId());
        }
        if (appBalance.getId() != null) {
            balance = appBalanceService.selectById(appBalance.getId());
            appBalance.setUserId(balance.getUserId());
        }
        assert balance != null;
        appBalanceService.changeBalance(appBalance.getUserId(), "人工操作_" + System.currentTimeMillis(), appBalance.getBalance().subtract(balance.getBalance()));
        return success();
    }


    /**
     * 删除用户余额
     */
    @PreAuthorize("@ss.hasPermi('user:balance:remove')")
    @Log(title = "用户余额", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(appBalanceService.deleteByIds(ids));
    }
}
