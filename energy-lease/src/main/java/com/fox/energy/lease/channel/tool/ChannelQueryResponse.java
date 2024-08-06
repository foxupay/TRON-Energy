package com.fox.energy.lease.channel.tool;

public class ChannelQueryResponse {

    /**
     * 接口请求状态
     */
    private boolean success;
    private String message;
    /**
     * 交易状态
     */
    private ChannelPayEnums state;
    private String hash;
    /**
     * 资源回收时间
     */
    private String recoveryTime;

    public ChannelQueryResponse() {
    }

    public ChannelQueryResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public ChannelQueryResponse(boolean success, String message, ChannelPayEnums state) {
        this.success = success;
        this.message = message;
        this.state = state;
    }

    public ChannelQueryResponse(boolean success, String message, ChannelPayEnums state, String hash, String recoveryTime) {
        this.success = success;
        this.message = message;
        this.state = state;
        this.hash = hash;
        this.recoveryTime = recoveryTime;
    }

    public static ChannelQueryResponse success(ChannelPayEnums state, String hash, String recoveryTime) {
        return new ChannelQueryResponse(true, "请求成功", state, hash, recoveryTime);
    }

    public static ChannelQueryResponse error(String message) {
        return new ChannelQueryResponse(false, message);
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ChannelPayEnums getState() {
        return state;
    }

    public void setState(ChannelPayEnums state) {
        this.state = state;
    }


    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getRecoveryTime() {
        return recoveryTime;
    }

    public void setRecoveryTime(String recoveryTime) {
        this.recoveryTime = recoveryTime;
    }
}
