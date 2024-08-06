package com.fox.energy.framework.web.service;

import com.fox.energy.common.core.domain.model.LoginAppUser;
import com.fox.energy.common.core.redis.RedisCache;
import com.fox.energy.common.utils.ServletUtils;
import com.fox.energy.common.utils.ip.AddressUtils;
import com.fox.energy.common.utils.ip.IpUtils;
import com.fox.energy.common.utils.uuid.IdUtils;
import com.github.pagehelper.util.StringUtil;
import eu.bitwalker.useragentutils.UserAgent;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
public class TokenAppService {
    private static final Logger log = LoggerFactory.getLogger(TokenAppService.class);

    @Value("${token.header}")
    private String header;

    @Value("${token.secret}")
    private String secret;

    private static final Long MILLIS_MINUTE_TEN = 1200000L;

    @Resource
    private RedisCache redisCache;

    public LoginAppUser getLoginUser(HttpServletRequest request) {
        String token = getToken(request);
        if (StringUtil.isNotEmpty(token))
            try {
                Claims claims = parseToken(token);
                String uuid = (String) claims.get("app_login_user_key");
                String userKey = getTokenKey(uuid);
                return this.redisCache.getCacheObject(userKey);
            } catch (Exception e) {
                log.error("获取用户信息异常'{}'", e.getMessage());
            }
        return null;
    }

    public String createToken(LoginAppUser loginUser) {
        String token = IdUtils.fastUUID();
        loginUser.setToken(token);
        setUserAgent(loginUser);
        refreshToken(loginUser, true);
        Map<String, Object> claims = new HashMap<>();
        claims.put("app_login_user_key", token);
        return createToken(claims);
    }

    public void verifyToken(LoginAppUser loginUser) {
        long expireTime = loginUser.getExpireTime();
        long currentTime = System.currentTimeMillis();
        if (expireTime - currentTime <= MILLIS_MINUTE_TEN) {
            refreshToken(loginUser, false);
        }
    }

    public void refreshToken(LoginAppUser loginUser, boolean install) {
        loginUser.setLoginTime(System.currentTimeMillis());
        int refreshTime = 30;
        //10080*365*10
        int expireTime = 36792000;
        loginUser.setExpireTime(loginUser.getLoginTime() + (install ? expireTime : refreshTime) * 60000L);
        this.redisCache.setCacheObject(getTokenKey(loginUser.getToken()), loginUser, install ? expireTime : refreshTime, TimeUnit.MINUTES);
        this.redisCache.setCacheObject(getUserToken(loginUser.getUserId().toString(), loginUser.getToken()), loginUser.getToken(), install ? expireTime : refreshTime, TimeUnit.MINUTES);
    }

    public void setUserAgent(LoginAppUser loginUser) {
        UserAgent userAgent = UserAgent.parseUserAgentString(ServletUtils.getRequest().getHeader("User-Agent"));
        String ip = IpUtils.getIpAddr();
        loginUser.setIpaddr(ip);
        loginUser.setLoginLocation(AddressUtils.getRealAddressByIP(ip));
        loginUser.setBrowser(userAgent.getBrowser().getName());
        loginUser.setOs(userAgent.getOperatingSystem().getName());
    }

    private String createToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, this.secret).compact();
    }

    public Claims parseToken(String token) {
        return Jwts.parser()
                .setSigningKey(this.secret)
                .parseClaimsJws(token)
                .getBody();
    }

    private String getToken(HttpServletRequest request) {
        String token = request.getHeader(this.header);
        if (StringUtil.isNotEmpty(token) && token.startsWith("Bearer ")) {
            token = token.replace("Bearer ", "");
        }
        return token;
    }

    public String getTokenKey(String uuid) {
        return "app_login_tokens:" + uuid;
    }

    public String getUserKey(String userId) {
        return "app_login_users:" + userId;
    }

    public String getUserToken(String userId, String token) {
        return "app_login_users:" + userId + ":" + token;
    }
}
