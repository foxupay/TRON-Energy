package com.fox.energy.framework.web.service;

import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.IdUtil;
import cn.hutool.crypto.SecureUtil;
import com.fox.energy.common.constant.CacheConstants;
import com.fox.energy.common.core.domain.CommonResult;
import com.fox.energy.common.core.domain.model.LoginAppUser;
import com.fox.energy.common.core.redis.RedisCache;
import com.fox.energy.common.enums.AppUserStatus;
import com.fox.energy.common.exception.ServiceException;
import com.fox.energy.common.exception.user.BlackListException;
import com.fox.energy.common.exception.user.UserNotExistsException;
import com.fox.energy.common.exception.user.UserPasswordRetryLimitExceedException;
import com.fox.energy.common.utils.CodeUtils;
import com.fox.energy.common.utils.MessageUtils;
import com.fox.energy.common.utils.ip.IpUtils;
import com.fox.energy.common.utils.security.RsaUtils;
import com.fox.energy.framework.config.MailUtils;
import com.fox.energy.system.service.ISysConfigService;
import com.fox.energy.user.domain.AppAccount;
import com.fox.energy.user.service.IAppAccountService;
import com.github.pagehelper.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class UserLoginService extends CommonResult {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private IAppAccountService appAccountService;
    @Autowired
    private ISysConfigService configService;
    @Autowired
    private TokenAppService tokenAppService;
    @Autowired
    private RedisCache redisCache;
    @Value("${user.password.maxRetryCount}")
    private int maxRetryCount;
    @Value("${user.password.lockTime}")
    private int lockTime;

    /**
     * 密码登录
     *
     * @param account
     * @param password
     * @return
     */
    public CommonResult emailByPwd(String account, String password) {

        if (StringUtil.isEmpty(account) || StringUtil.isEmpty(password)) {
            throw new UserNotExistsException();
        }
        //黑名单检测
        String blackStr = this.configService.selectConfigByKey("sys.login.blackIPList");
        if (IpUtils.isMatchedIp(blackStr, IpUtils.getIpAddr())) {
            throw new BlackListException();
        }
        //邮箱格式检测
        if (!Validator.isEmail(account)) {
            return error(MessageUtils.message("app.user.login.email.not.valid", new Object[0]));
        }
        //密码解密
        try {
            password = RsaUtils.decryptByPrivateKey(password.replace(" ", "+"));
        } catch (Exception e) {
            return error(MessageUtils.message("app.user.login.password.error", new Object[0]));
        }
        //失败次数校验
        checkRetryCount(account);

        AppAccount appAccount = appAccountService.selectByEmail(account);
        if (appAccount == null) {
            throw new ServiceException(MessageUtils.message("app.user.not.exists"));
        }

        //校验用户状态
        if (AppUserStatus.DISABLE.getCode() == appAccount.getStatus()) {
            this.logger.info("登录用户：{} 已被停用.", appAccount.getEmail());
            throw new ServiceException(MessageUtils.message("user.blocked"));
        }
        //校验密码
        if (!SecureUtil.sha256(appAccount.getId() + password).equals(appAccount.getSign())) {
            addRetryCount(account);
            throw new ServiceException(MessageUtils.message("app.user.login.password.not.match"));
        }
        clearRetryCount(account);

        LoginAppUser appUser = new LoginAppUser();
        appUser.setUserId(appAccount.getId());
        String token = tokenAppService.createToken(appUser);
        return CommonResult.success().set("token", token);
    }

    public CommonResult emailByPwdNoEncrypt(String account, String password) {

        if (StringUtil.isEmpty(account) || StringUtil.isEmpty(password)) {
            throw new UserNotExistsException();
        }
        //黑名单检测
        String blackStr = this.configService.selectConfigByKey("sys.login.blackIPList");
        if (IpUtils.isMatchedIp(blackStr, IpUtils.getIpAddr())) {
            throw new BlackListException();
        }
        //邮箱格式检测
        if (!Validator.isEmail(account)) {
            return error(MessageUtils.message("app.user.login.email.not.valid", new Object[0]));
        }
        //失败次数校验
        checkRetryCount(account);

        AppAccount appAccount = appAccountService.selectByEmail(account);
        if (appAccount == null) {
            throw new ServiceException(MessageUtils.message("app.user.not.exists"));
        }

        //校验用户状态
        if (AppUserStatus.DISABLE.getCode() == appAccount.getStatus()) {
            this.logger.info("登录用户：{} 已被停用.", appAccount.getEmail());
            throw new ServiceException(MessageUtils.message("user.blocked"));
        }
        //校验密码
        if (!SecureUtil.sha256(appAccount.getId() + password).equals(appAccount.getSign())) {
            addRetryCount(account);
            throw new ServiceException(MessageUtils.message("app.user.login.password.not.match"));
        }
        clearRetryCount(account);

        LoginAppUser appUser = new LoginAppUser();
        appUser.setUserId(appAccount.getId());
        String token = tokenAppService.createToken(appUser);
        return CommonResult.success().set("token", token).set("uid", appAccount.getId());
    }

    /**
     * 验证码登录-发送验证码
     *
     * @param account
     * @return
     */
    public CommonResult sendCode(String account) {
        if (!Validator.isEmail(account)) {
            return error(MessageUtils.message("app.user.login.email.not.valid"));
        }
        String code = CodeUtils.createNumbers(6);
        this.logger.info("注册 发送邮件 发送：{}-{}", account, code);
        MailUtils.send(account, "Identifying code", code, true);
        this.redisCache.setCacheObject(CacheConstants.USER_LOGIN_CODE_KEY.key() + account, code, 10, TimeUnit.MINUTES);
        return CommonResult.success();
    }

    /**
     * 验证码登录-登录
     *
     * @param account
     * @param code
     * @return
     */
    public CommonResult emailByCode(String account, String code) {
        if (StringUtil.isEmpty(account) || StringUtil.isEmpty(code)) {
            throw new UserNotExistsException();
        }
        //黑名单检测
        String blackStr = this.configService.selectConfigByKey("sys.login.blackIPList");
        if (IpUtils.isMatchedIp(blackStr, IpUtils.getIpAddr())) {
            throw new BlackListException();
        }
        //邮箱格式检测
        if (!Validator.isEmail(account)) {
            return error(MessageUtils.message("app.user.login.email.not.valid", new Object[0]));
        }
        //失败次数校验
        checkRetryCount(account);

        AppAccount appAccount = appAccountService.selectByEmail(account);
        if (appAccount == null) {
            //走注册流程
            appAccount = new AppAccount();
            appAccount.setNickName(IdUtil.fastSimpleUUID().substring(0, 8).toUpperCase());
            appAccount.setEmail(account);
            appAccount.setStatus(1);
            appAccountService.insert(appAccount);
        }

        //校验用户状态
        if (AppUserStatus.DISABLE.getCode() == appAccount.getStatus()) {
            this.logger.info("登录用户：{} 已被停用.", appAccount.getEmail());
            throw new ServiceException(MessageUtils.message("user.blocked"));
        }
        //验证验证码
        String codeKey = CacheConstants.USER_LOGIN_CODE_KEY.key() + account;
        if (!code.equals(this.redisCache.getCacheObject(codeKey))) {
            throw new ServiceException(MessageUtils.message("app.user.login.verify.code.error"));
        }
        this.redisCache.deleteObject(codeKey);
        clearRetryCount(account);
        LoginAppUser appUser = new LoginAppUser();
        appUser.setUserId(appAccount.getId());
        String token = tokenAppService.createToken(appUser);
        return CommonResult.success().set("token", token);
    }


    private void checkRetryCount(String username) {
        Integer retryCount = this.redisCache.getCacheObject(getCacheKey(username));
        if (retryCount == null) {
            retryCount = 0;
        }
        if (retryCount >= this.maxRetryCount) {
            throw new UserPasswordRetryLimitExceedException(this.maxRetryCount, this.lockTime);
        }
    }

    private void addRetryCount(String username) {
        Integer retryCount = this.redisCache.getCacheObject(getCacheKey(username));
        if (retryCount == null) {
            retryCount = 0;
        }
        retryCount = retryCount + 1;
        this.redisCache.setCacheObject(getCacheKey(username), retryCount, this.lockTime, TimeUnit.MINUTES);
    }

    private String getCacheKey(String username) {
        return CacheConstants.PWD_ERR_CNT_KEY.key() + username;
    }

    public void clearRetryCount(String username) {
        if (this.redisCache.hasKey(getCacheKey(username))) {
            this.redisCache.deleteObject(getCacheKey(username));
        }
    }
}
