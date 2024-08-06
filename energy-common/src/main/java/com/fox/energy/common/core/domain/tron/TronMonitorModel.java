package com.fox.energy.common.core.domain.tron;

public class TronMonitorModel {

    private String type;
    private String address;

    private String fromAddress;
    private String txID;
    private String contract;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFromAddress() {
        return fromAddress;
    }

    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }

    public String getTxID() {
        return txID;
    }

    public void setTxID(String txID) {
        this.txID = txID;
    }

    public String getContract() {
        return contract;
    }

    public void setContract(String contract) {
        this.contract = contract;
    }

    public boolean isContract() {
        return "contract".equals(this.type);
    }

    public boolean isUSDT() {
        return "TR7NHqjeKQxGTCi8q8ZY4pL8otSzgjLj6t".equals(this.contract);
    }
}
