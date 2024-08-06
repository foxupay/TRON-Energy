package com.fox.energy.common.enums;

/**
 * 业务操作类型
 *
 * @author ruoyi
 */
public enum TronToken {
    TRX("trx"),
    USDT("usdt"),
    ;

    TronToken(String token) {
        this.token = token;
    }

    private String token;

    public String getToken() {
        return token;
    }
}
