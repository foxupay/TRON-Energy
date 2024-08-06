package com.fox.energy.web.app.user;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.DesensitizedUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.fox.energy.app.domain.AppApiKey;
import com.fox.energy.app.service.IAppApiKeyService;
import com.fox.energy.common.annotation.APILog;
import com.fox.energy.common.annotation.AppLogin;
import com.fox.energy.common.annotation.Log;
import com.fox.energy.common.constant.CacheConstants;
import com.fox.energy.common.core.controller.BaseController;
import com.fox.energy.common.core.domain.AjaxResult;
import com.fox.energy.common.core.page.TableDataInfo;
import com.fox.energy.common.core.redis.RedisCache;
import com.fox.energy.common.enums.BusinessType;
import com.fox.energy.common.exception.ServiceException;
import com.fox.energy.common.utils.CodeUtils;
import com.fox.energy.common.utils.LocalUserID;
import com.fox.energy.common.utils.MessageUtils;
import com.fox.energy.framework.config.MailUtils;
import com.fox.energy.tron.server.TronAddressServer;
import com.fox.energy.user.domain.AppAccount;
import com.fox.energy.user.domain.AppBalance;
import com.fox.energy.user.domain.AppBalanceAddress;
import com.fox.energy.user.domain.AppBalanceLog;
import com.fox.energy.user.service.IAppAccountService;
import com.fox.energy.user.service.IAppBalanceAddressService;
import com.fox.energy.user.service.IAppBalanceLogService;
import com.fox.energy.user.service.IAppBalanceService;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Api(tags = {"2-APP-账户"})
@RestController
@RequestMapping({"/app/user/info"})
public class UserInfoController extends BaseController {

    @Autowired
    private IAppAccountService appAccountService;
    @Autowired
    private IAppBalanceService appBalanceService;
    @Autowired
    private IAppBalanceAddressService appBalanceAddressService;
    @Autowired
    private IAppBalanceLogService appBalanceLogService;
    @Resource
    private TronAddressServer tronAddressServer;
    @Resource
    private RedisCache redisCache;
    @Autowired
    private IAppApiKeyService appApiKeyService;


    @ApiOperation("账户-账户信息")
    @APILog(title = "账户-账户信息")
    @PostMapping({""})
    @AppLogin
    public AjaxResult account() {
        AppAccount appAccount = appAccountService.selectById(LocalUserID.get());
        return success()
                .set("account", appAccount.getEmail())
                .set("rechargeAddress", tronAddressServer.getUserAddress(LocalUserID.getL()))
                ;
    }


    @ApiOperation("账户-查询余额")
    @APILog(title = "账户-查询余额")
    @PostMapping({"/balance"})
    @AppLogin
    public AjaxResult balance() {
        AppBalance appBalance = appBalanceService.selectByUserId(LocalUserID.getL());
        return success().set("balance", appBalance.getBalance());
    }

    @ApiOperation("账户-查询余额明细")
    @APILog(title = "账户-查询余额明细")
    @PostMapping({"/balance/log"})
    @AppLogin
    public TableDataInfo balanceLog() {
        startPage();
        PageHelper.orderBy("id desc");
        AppBalanceLog log = new AppBalanceLog();
        log.setUserId(LocalUserID.getStr());
        List<AppBalanceLog> list = appBalanceLogService.selectList(log);
        return getDataTable(list);
    }

    @ApiOperation("账户-地址余额")
    @APILog(title = "账户-地址余额")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "address", value = "地址", required = true, dataType = "String", dataTypeClass = String.class)
    })
    @GetMapping({"/balance/address"})
    public AjaxResult balanceAddress(@NotNull @RequestParam String address) {
        AppBalanceAddress appBalanceAddress = appBalanceAddressService.getByAddress(address);
        return success().set("balance", appBalanceAddress == null ? 0 : appBalanceAddress.getBalance());
    }


    @ApiOperation("账户-设置登录密码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "password", value = "密码", required = true, dataType = "String", dataTypeClass = String.class)
    })
    @APILog(title = "账户-设置登录密码")
    @PostMapping({"/set/pwd"})
    @AppLogin
    public AjaxResult setPwd(@NotNull @RequestParam String password) {
        return toAjax(appAccountService.resetPwd(LocalUserID.get(), password));
    }

    @ApiOperation("账户-注销-发送验证码")
    @APILog(title = "账户-注销-发送验证码")
    @GetMapping({"/logout"})
    @AppLogin
    public AjaxResult logoutCode() {
        AppAccount appAccount = appAccountService.selectById(LocalUserID.get());
        String code = CodeUtils.createNumbers(6);
        this.logger.info("注册 发送邮件 发送：{}-{}", appAccount.getEmail(), code);
        MailUtils.send(appAccount.getEmail(), "Identifying code", code, true);
        this.redisCache.setCacheObject(CacheConstants.USER_LOGOUT_CODE_KEY.key() + appAccount.getEmail(), code, 10, TimeUnit.MINUTES);
        return success();
    }


    @ApiOperation("账户-注销")
    @APILog(title = "账户-注销")
    @PostMapping({"/logout"})
    @AppLogin
    public AjaxResult logout(@RequestParam String code) {
        AppAccount appAccount = appAccountService.selectById(LocalUserID.get());
        //验证验证码
        String codeKey = CacheConstants.USER_LOGOUT_CODE_KEY.key() + appAccount.getEmail();
        if (!code.equals(this.redisCache.getCacheObject(codeKey))) {
            throw new ServiceException(MessageUtils.message("app.user.login.verify.code.error"));
        }
        this.redisCache.deleteObject(codeKey);

        //判断余额
        AppBalance appBalance = appBalanceService.selectByUserId(LocalUserID.getL());
        if (appBalance.getBalance().doubleValue() > 0) {
            return error("账户余额不为0，不可注销");
        }
        AppAccount account = new AppAccount();
        account.setId(appAccount.getId());
        account.setEmail(appAccount.getEmail() + "_" + DateUtil.format(DateUtil.date(), "yyyyMMddHHmmss"));
        appAccountService.update(account);
        return success();
    }

    @ApiOperation("账户-API秘钥")
    @APILog(title = "账户-API秘钥")
    @GetMapping({"/apiKey"})
    @AppLogin
    public AjaxResult apiKey() {
        AppApiKey apiKey = new AppApiKey();
        apiKey.setUserId(LocalUserID.get());
        List<AppApiKey> appApiKeys = appApiKeyService.selectList(apiKey);
        return success(appApiKeys);
    }

    @ApiOperation("账户-API秘钥新增")
    @APILog(title = "账户-API秘钥新增")
    @PostMapping({"/apiKey"})
    @AppLogin
    public AjaxResult apiKeyAdd(AppApiKey apiKey) {
        apiKey.setUserId(LocalUserID.get());
        String md5 = SecureUtil.md5(SecureUtil.sha256(LocalUserID.getStr()));
        apiKey.setApiKey(md5.substring(0, 16) + SecureUtil.md5(System.currentTimeMillis() + "") + md5.substring(6));
        appApiKeyService.insert(apiKey);
        return success();
    }

    @ApiOperation("账户-API秘钥删除")
    @APILog(title = "账户-API秘钥删除")
    @AppLogin
    @DeleteMapping("/{id}")
    public AjaxResult apiKeyRemove(@PathVariable Integer id) {
        return toAjax(appApiKeyService.deleteById(id));
    }

}
