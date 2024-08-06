package com.fox.energy.lease.channel.tool;

public class ChannelPayResponse {

    private boolean success;
    private String message;
    private ChannelPayEnums state;
    private String orderId;
    private String hash;

    public ChannelPayResponse() {
    }

    public ChannelPayResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public ChannelPayResponse(boolean success, String message, ChannelPayEnums state) {
        this.success = success;
        this.message = message;
        this.state = state;
    }

    public ChannelPayResponse(boolean success, String message, ChannelPayEnums state, String orderId, String hash) {
        this.success = success;
        this.message = message;
        this.state = state;
        this.orderId = orderId;
        this.hash = hash;
    }

    public static ChannelPayResponse success(ChannelPayEnums state, String orderId, String hash) {
        return new ChannelPayResponse(true, "请求成功", state, orderId, hash);
    }

    public static ChannelPayResponse error(String message) {
        return new ChannelPayResponse(false, message);
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

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }
}
