package com.fox.energy.tron.domain;

import java.math.BigDecimal;

/**
 * TRON地址对象 tron_address
 *
 * @author IEME
 * @date 2024-05-15
 */
public class TronAddress {

    /**
     *
     */
    private Long id;
    /**
     * 用户ID，与地址关联
     */
    private Long userId;
    /**
     * 地址类型 1动态地址 2固定地址
     */
    private Integer type;
    /**
     * 地址
     */
    private String address;
    /**
     * 秘钥
     */
    private String privateKey;
    /**
     * TRX余额
     */
    private BigDecimal amtTrx;
    /**
     * USDT余额
     */
    private BigDecimal amtUsdt;
    /**
     * 是否已启用 1未启用 2启用中 3已启用
     */
    private Integer enable;
    /**
     * 启用时间
     */
    private String enableTime;
    /**
     * 启用Hash
     */
    private String enableHash;
    /**
     * 是否在使用 1未使用 2使用中 地址类型为1时生效
     */
    private Integer used;
    /**
     * 使用释放时间
     */
    private String usedTime;
    private String createTime;
    private String updateTime;


    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }


    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }


    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getType() {
        return type;
    }


    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }


    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getPrivateKey() {
        return privateKey;
    }


    public void setEnable(Integer enable) {
        this.enable = enable;
    }

    public Integer getEnable() {
        return enable;
    }


    public void setEnableTime(String enableTime) {
        this.enableTime = enableTime;
    }

    public String getEnableTime() {
        return enableTime;
    }


    public void setEnableHash(String enableHash) {
        this.enableHash = enableHash;
    }

    public String getEnableHash() {
        return enableHash;
    }


    public void setUsed(Integer used) {
        this.used = used;
    }

    public Integer getUsed() {
        return used;
    }


    public void setUsedTime(String usedTime) {
        this.usedTime = usedTime;
    }

    public String getUsedTime() {
        return usedTime;
    }


    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCreateTime() {
        return createTime;
    }


    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }


    public BigDecimal getAmtTrx() {
        return amtTrx;
    }

    public void setAmtTrx(BigDecimal amtTrx) {
        this.amtTrx = amtTrx;
    }

    public BigDecimal getAmtUsdt() {
        return amtUsdt;
    }

    public void setAmtUsdt(BigDecimal amtUsdt) {
        this.amtUsdt = amtUsdt;
    }
}