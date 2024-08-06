package com.fox.energy.common.enums;

public enum AppUserStatus {
    OK(1, "正常"),
    DISABLE(2, "禁用");

    private final String info;

    private final int code;

    AppUserStatus(int code, String info) {
        this.code = code;
        this.info = info;
    }

    public int getCode() {
        return this.code;
    }

    public String getInfo() {
        return this.info;
    }
}
