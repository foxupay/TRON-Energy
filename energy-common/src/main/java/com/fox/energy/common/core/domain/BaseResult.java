package com.fox.energy.common.core.domain;

public class BaseResult {
    private int code;

    private String message;

    private Object data;

    public BaseResult(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public Object getData() {
        return this.data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public BaseResult() {
    }

    public BaseResult(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static BaseResult success() {
        return new BaseResult(200, "success");
    }

    public static BaseResult success(String message) {
        return new BaseResult(200, message);
    }

    public static BaseResult success(Object data) {
        return new BaseResult(200, "success", data);
    }

    public static BaseResult error(int code, String message) {
        return new BaseResult(code, message);
    }

    public static BaseResult error(String message) {
        return new BaseResult(-1, message);
    }

    public int getCode() {
        return this.code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
