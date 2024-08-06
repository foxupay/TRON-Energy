package com.fox.energy.framework.aspectj;

import com.fox.energy.common.constant.Constants;
import com.fox.energy.common.constant.CookieConstants;
import com.fox.energy.common.core.domain.model.LoginAppUser;
import com.fox.energy.common.enums.AppUserStatus;
import com.fox.energy.common.utils.CookieUtil;
import com.fox.energy.common.utils.LocalUserID;
import com.fox.energy.common.utils.StringUtils;
import com.fox.energy.common.utils.uuid.IdUtils;
import com.fox.energy.framework.web.service.TokenAppService;
import com.fox.energy.user.domain.AppAccount;
import com.fox.energy.user.service.IAppAccountService;
import com.github.pagehelper.util.StringUtil;
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
public class AppLoginNotRequiredAspect {
    private static final Logger log = LoggerFactory.getLogger(AppLoginNotRequiredAspect.class);

    @Value("${token.header}")
    private String header;

    @Resource
    private TokenAppService tokenAppService;

    @Resource
    private IAppAccountService appAccountService;

    @Pointcut("@annotation( com.fox.energy.common.annotation.AppLoginNotRequired)")
    public void controllerAspect() {
    }

    @Before("controllerAspect()")
    public void before() {
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        String token = getToken(request);
        if (StringUtil.isNotEmpty(token)) {
            LoginAppUser loginUser = this.tokenAppService.getLoginUser(request);
            if (loginUser != null) {
                this.tokenAppService.verifyToken(loginUser);
                AppAccount user = this.appAccountService.selectById(loginUser.getUserId());
                if (AppUserStatus.DISABLE.getCode() != user.getStatus()) {
                    LocalUserID.put(loginUser.getUserId());
                }
            }
        } else {
            String TOKEN_TEMPORARY = CookieUtil.get(CookieConstants.FOX_ENERGY_TOKEN_TEMPORARY);
            if (StringUtils.isNotEmpty(TOKEN_TEMPORARY) && TOKEN_TEMPORARY.length() > 16) {
                LocalUserID.put(TOKEN_TEMPORARY);
            } else {
                TOKEN_TEMPORARY = IdUtils.fastSimpleUUID();
                CookieUtil.set(CookieConstants.FOX_ENERGY_TOKEN_TEMPORARY, TOKEN_TEMPORARY);
                LocalUserID.put(TOKEN_TEMPORARY);
            }
        }
    }

    @After("controllerAspect()")
    public void after() {
        LocalUserID.remove();
    }

    private String getToken(HttpServletRequest request) {
        String token = request.getHeader(this.header);
        if (StringUtil.isNotEmpty(token) && token.startsWith(Constants.TOKEN_PREFIX.trim())) {
            token = token.replaceAll(Constants.TOKEN_PREFIX.trim(), "");
            token = token.replaceAll(" ", "");
        }
        return token;
    }


}
