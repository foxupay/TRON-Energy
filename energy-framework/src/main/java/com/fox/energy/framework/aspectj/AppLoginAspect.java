package com.fox.energy.framework.aspectj;

import com.fox.energy.common.constant.Constants;
import com.fox.energy.common.core.domain.model.LoginAppUser;
import com.fox.energy.common.enums.AppUserStatus;
import com.fox.energy.common.exception.ServiceException;
import com.fox.energy.common.utils.LocalUserID;
import com.fox.energy.common.utils.MessageUtils;
import com.fox.energy.framework.web.service.TokenAppService;
import com.fox.energy.user.domain.AppAccount;
import com.fox.energy.user.service.IAppAccountService;
import com.github.pagehelper.util.StringUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@Aspect
@Component
@Order(1)
public class AppLoginAspect {
    private static final Logger log = LoggerFactory.getLogger(AppLoginAspect.class);

    @Value("${token.header}")
    private String header;

    @Value("${token.secret}")
    private String secret;

    @Resource
    private TokenAppService tokenAppService;

    @Resource
    private IAppAccountService appAccountService;

    @Pointcut("@annotation( com.fox.energy.common.annotation.AppLogin)")
    public void controllerAspect() {
    }

    @Before("controllerAspect()")
    public void befores(JoinPoint joinPoint) {
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();

        String className = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        String method = className + "." + methodName + "()";
        String token = getToken(request);
        if (StringUtil.isEmpty(token)) {
            log.info(">>> 请求方法：{}", method);
            throw new ServiceException(MessageUtils.message("app.user.login.error"), 9999);
        }
        LoginAppUser loginUser = this.tokenAppService.getLoginUser(request);
        if (loginUser == null) {
            throw new ServiceException(MessageUtils.message("app.user.login.error"), 9999);
        }
        this.tokenAppService.verifyToken(loginUser);
        AppAccount user = this.appAccountService.selectById(loginUser.getUserId());
        if (AppUserStatus.DISABLE.getCode() == user.getStatus()) {
            log.info("登录用户：{} 已被停用.", user.getId());
            throw new ServiceException(MessageUtils.message("user.blocked", new Object[0]));
        }
        log.info(">>> USERID   >>> {}", loginUser.getUserId());
        LocalUserID.put(loginUser.getUserId());
    }

    @After("controllerAspect()")
    public void after() {
        LocalUserID.remove();
    }

    private String getToken(HttpServletRequest request) {
        String token = request.getHeader(this.header);
        log.info("token:{}", token);
        if (StringUtil.isNotEmpty(token) && token.startsWith(Constants.TOKEN_PREFIX)) {
            token = token.replace(Constants.TOKEN_PREFIX, "");
        }
        return token;
    }


}
