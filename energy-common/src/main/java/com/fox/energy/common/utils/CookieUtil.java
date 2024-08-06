package com.fox.energy.common.utils;

import cn.hutool.core.util.URLUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * cookie工具类
 */
public class CookieUtil {

    private static Logger log = LoggerFactory.getLogger(CookieUtil.class);

    /**
     * 设置
     *
     * @param name
     * @param value
     */
    public static void set(String name, String value) {
        log.info("CookieSet {}:{}", name, value);
        Cookie cookie = new Cookie(name, URLUtil.encode(value));
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60 * 24 * 365);
        ServletUtils.getResponse().addCookie(cookie);
    }

    /**
     * 设置
     *
     * @param name
     * @param value
     * @param maxAge
     */
    public static void setReal(String name, String value, int maxAge) {
        log.info("CookieSet {}:{}:{}", name, value, maxAge);
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);
        ServletUtils.getResponse().addCookie(cookie);
    }

    /**
     * 获取cookie
     * 根据name 找 值
     *
     * @param name
     * @return
     */
    public static String get(String name) {
        Map<String, String> cookieMap = readCookieStrMap();
        String value = "";
        if (cookieMap.containsKey(name)) {//检查这个名字的map是否存在 存在返回true  不存在返回false
            value = URLUtil.decode(cookieMap.get(name));//拿到这个value
        }
        log.info("CookieGet {}:{}", name, value);
        return value;
    }


    /**
     * 将cookie封装成Map
     * 取到所有的Cookie  并把一个个Cookie存入到Map集合里面 key是Cookie名字  value是Cookie
     *
     * @return
     */
    public static Map<String, Cookie> readCookieMap() {
        Map<String, Cookie> cookieMap = new HashMap<>();
        Cookie[] cookies = ServletUtils.getRequest().getCookies();//返回所有的Cookie
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                cookieMap.put(cookie.getName(), cookie);
            }
        }
        return cookieMap;
    }

    /**
     * 将cookie封装成Map
     * 取到所有的Cookie  并把一个个Cookie存入到Map集合里面 key是Cookie名字  value是Cookie
     *
     * @return
     */
    public static Map<String, String> readCookieStrMap() {
        Map<String, String> cookieMap = new HashMap<>();
        Cookie[] cookies = ServletUtils.getRequest().getCookies();//返回所有的Cookie
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                cookieMap.put(cookie.getName(), cookie.getValue());
            }
        }
        return cookieMap;
    }
}
