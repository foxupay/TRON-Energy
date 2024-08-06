package com.fox.energy.common.constant;

/**
 * 缓存的key 常量
 *
 * @author ruoyi
 */
public enum CacheConstants {
    LOGIN_TOKEN_KEY("login_tokens", "用户信息"),
    CAPTCHA_CODE_KEY("captcha_codes", "验证码"),
    SYS_CONFIG_KEY("sys_config", "配置信息"),
    SYS_DICT_KEY("sys_dict", "数据字典"),
    REPEAT_SUBMIT_KEY("repeat_submit", "防重提交"),
    RATE_LIMIT_KEY("rate_limit", "限流处理"),
    PWD_ERR_CNT_KEY("pwd_err_cnt", "密码错误次数"),
    APP_CLIENT_LANG("app_client_lang", "客户端语言"),
    USER_LOGIN_CODE_KEY("user_login_code", "登录验证"),
    USER_LOGOUT_CODE_KEY("user_logout_code", "注销验证"),
    LEASE_PRICE_KEY("lease_price", "能量价格"),
    LEASE_RATE_KEY("lease_rate", "USDT/TRX汇率"),
    ;

    private static final String PREFIX = "energy:";
    private static final String SUFFIX = ":";
    private String key;
    private String desc;

    CacheConstants(String key, String desc) {
        this.key = key;
        this.desc = desc;
    }

    public String key() {
        return PREFIX + key + SUFFIX;
    }

    public String desc() {
        return desc;
    }
}
