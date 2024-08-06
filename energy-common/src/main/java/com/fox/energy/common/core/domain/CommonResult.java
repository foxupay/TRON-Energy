package com.fox.energy.common.core.domain;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

public class CommonResult {
    private boolean success;

    private String message;

    private JSONObject data = new JSONObject();

    public CommonResult() {
    }

    public CommonResult(boolean success, String message, JSONObject data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public CommonResult(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public static CommonResult success() {
        return new CommonResult(true, "success");
    }

    public static CommonResult success(String message) {
        return new CommonResult(true, message);
    }

    public static CommonResult success(JSONObject data) {
        return new CommonResult(true, "success", data);
    }


    public CommonResult set(String k, Object v) {
        this.data.set(k, v);
        return this;
    }

    public static CommonResult error(String message) {
        return new CommonResult(false, message);
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public JSONObject getData() {
        return data;
    }

    public void setData(JSONObject data) {
        this.data = data;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return JSONUtil.toJsonStr(this);
    }
}
