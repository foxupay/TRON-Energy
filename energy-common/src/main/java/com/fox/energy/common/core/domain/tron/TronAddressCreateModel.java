package com.fox.energy.common.core.domain.tron;

public class TronAddressCreateModel {
    private String address;
    private String hexAddress;
    private String privateKey;
    private String publicKey;

    public TronAddressCreateModel(String address, String hexAddress, String privateKey, String publicKey) {
        this.address = address;
        this.hexAddress = hexAddress;
        this.privateKey = privateKey;
        this.publicKey = publicKey;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHexAddress() {
        return hexAddress;
    }

    public void setHexAddress(String hexAddress) {
        this.hexAddress = hexAddress;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }
}
