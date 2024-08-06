package com.fox.energy.common.core.domain.tron;

public class TronAddressActivateResponse {
    private boolean activated;
    private String hash;

    public TronAddressActivateResponse() {
    }

    public TronAddressActivateResponse(boolean activated, String hash) {
        this.activated = activated;
        this.hash = hash;
    }

    public TronAddressActivateResponse(boolean activated) {
        this.activated = activated;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }
}
