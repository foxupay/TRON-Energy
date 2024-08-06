package com.fox.energy.web.admin.user;

import cn.hutool.core.bean.BeanUtil;
import com.fox.energy.common.core.controller.BaseController;
import com.fox.energy.common.core.domain.AjaxResult;
import com.fox.energy.common.core.page.TableDataInfo;
import com.fox.energy.user.domain.AppAccount;
import com.fox.energy.user.domain.vo.AppAccountVo;
import com.fox.energy.user.service.IAppAccountService;
import com.fox.energy.user.service.IAppBalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 客户端账户体系Controller
 *
 * @author IEME
 * @date 2024-04-23
 */
@RestController
@RequestMapping("/user/account")
public class AppAccountController extends BaseController {
    @Autowired
    private IAppAccountService appAccountService;

    @Autowired
    private IAppBalanceService appBalanceService;


    /**
     * 查询客户端账户体系列表
     */
    @PreAuthorize("@ss.hasPermi('user:account:list')")
    @GetMapping("/list")
    public TableDataInfo list(AppAccount appAccount) {
        startPage();
        List<AppAccount> list = appAccountService.selectList(appAccount);
        TableDataInfo dataTable = getDataTable(list);
        List<AppAccountVo> vos = new ArrayList<>();
        for (AppAccount account : list) {
            AppAccountVo accountVo = BeanUtil.copyProperties(account, AppAccountVo.class);
            accountVo.setBalance(appBalanceService.selectByUserId(accountVo.getId().longValue()).getBalance());
            vos.add(accountVo);
        }
        dataTable.setRows(vos);
        return dataTable;
    }


    /**
     * 查询客户端账户体系列表
     */
    @PreAuthorize("@ss.hasPermi('user:account:edit')")
    @PostMapping("/enable/{id}")
    public AjaxResult enable(@PathVariable Integer id) {
        AppAccount appAccount = appAccountService.selectById(id);
        if (appAccount == null) {
            return error("账户不存在");
        }
        AppAccount account = new AppAccount();
        account.setId(appAccount.getId());
        account.setStatus(appAccount.getStatus() == 1 ? 2 : 1);
        appAccountService.update(account);
        return success();
    }
}
