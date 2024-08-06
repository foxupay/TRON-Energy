package com.fox.energy.common.core.domain.tron;

public class LeaseCreateResponse {
    private boolean success;
    private String message;
    private String orderId;
    private String hash;

    public LeaseCreateResponse(boolean success, String orderId, String hash) {
        this.success = success;
        this.orderId = orderId;
        this.hash = hash;
    }

    public LeaseCreateResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
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
