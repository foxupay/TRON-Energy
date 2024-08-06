package com.fox.energy.common.core.domain.tron;

public class TronTradeResult {
    //协议确认
    private boolean contractRet;

    //节点确认
    private boolean confirmed;

    private Integer confirmations;

    public TronTradeResult() {
    }

    public TronTradeResult(boolean contractRet, boolean confirmed, Integer confirmations) {
        this.contractRet = contractRet;
        this.confirmed = confirmed;
        this.confirmations = confirmations;
    }

    public Integer getConfirmations() {
        return confirmations;
    }

    public void setConfirmations(Integer confirmations) {
        this.confirmations = confirmations;
    }

    public boolean isContractRet() {
        return contractRet;
    }

    public void setContractRet(boolean contractRet) {
        this.contractRet = contractRet;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }
}
