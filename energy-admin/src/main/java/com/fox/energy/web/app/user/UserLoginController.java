package com.fox.energy.web.app.user;

import com.fox.energy.common.annotation.APILog;
import com.fox.energy.common.annotation.RateLimiter;
import com.fox.energy.common.core.controller.BaseController;
import com.fox.energy.common.core.domain.AjaxResult;
import com.fox.energy.common.enums.LimitType;
import com.fox.energy.framework.web.service.UserLoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"1-APP-账户登录"})
@RestController
@RequestMapping({"/app/user/login"})
public class UserLoginController extends BaseController {
    @Autowired
    private UserLoginService userLoginService;

    @ApiOperation("登录-密码登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "account", value = "邮箱", required = true, dataType = "String", dataTypeClass = String.class),
            @ApiImplicitParam(name = "password", value = "密码", required = true, dataType = "String", dataTypeClass = String.class)
    })
    @APILog(title = "登录-密码登录")
    @PostMapping({"/pass"})
    @RateLimiter(limitType = LimitType.DATA, count = 1, time = 1)
    public AjaxResult pass(@RequestParam String account, @RequestParam String password) {
        return result(userLoginService.emailByPwd(account, password));
    }


    @ApiOperation("登录-发送邮件验证码")
    @ApiImplicitParam(name = "account", value = "邮箱", required = true, dataType = "String", dataTypeClass = String.class)
    @APILog(title = "登录-发送邮件验证码")
    @GetMapping({"/code"})
    @RateLimiter(limitType = LimitType.DATA, count = 1, time = 1)
    public AjaxResult sendCode(@RequestParam String account) {
        return result(userLoginService.sendCode(account));
    }

    @ApiOperation("登录-验证码登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "account", value = "邮箱", required = true, dataType = "String", dataTypeClass = String.class),
            @ApiImplicitParam(name = "code", value = "验证码", required = true, dataType = "String", dataTypeClass = String.class)
    })
    @APILog(title = "登录-验证码登录")
    @PostMapping({"/code"})
    @RateLimiter(limitType = LimitType.DATA, count = 1, time = 1)
    public AjaxResult code(@RequestParam String account, @RequestParam String code) {
        return result(userLoginService.emailByCode(account, code));
    }

}
