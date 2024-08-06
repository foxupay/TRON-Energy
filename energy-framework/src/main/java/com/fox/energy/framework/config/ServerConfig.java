package com.fox.energy.framework.config;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;
import com.fox.energy.common.utils.ServletUtils;

/**
 * 服务相关配置
 *
 * @author ruoyi
 */
@Component
public class ServerConfig {
    /**
     * 获取完整的请求路径，包括：域名，端口，上下文访问路径
     *
     * @return 服务地址
     */
    public String getUrl() {
        HttpServletRequest request = ServletUtils.getRequest();
        return getDomain(request);
    }

    public static String getDomain(HttpServletRequest request) {
        StringBuilder url = new StringBuilder(request.getRequestURL());
        String contextPath = request.getServletContext().getContextPath();
        String rUrl = url.substring(0, url.length() - request.getRequestURI().length()) + contextPath;
        String port = request.getServerPort() == 80 ? "" : ":" + request.getServerPort();
        return rUrl.replace(":80", port);
    }

    public static String getURL(HttpServletRequest request) {
        String url = String.valueOf(request.getRequestURL());
        if (request.getServerPort() == 80) {
            url = url.replace(":80", "");
        }
        return url;
    }
}
