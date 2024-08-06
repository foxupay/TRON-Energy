package com.fox.energy.sweep.domain.vo;

import java.math.BigDecimal;

/**
 * 归集记录对象 tron_sweep_log
 *
 * @author IEME
 * @date 2024-05-16
 */
public class TronSweepLogVo {
    /**
     * 批次号
     */
    private String sweepNo;
    /**
     * 归集编号
     */
    private String sweepId;
    /**
     * 归集地址
     */
    private String address;
    /**
     * 归集代币类型 1-TRX 2-USDT
     */
    private Integer type;
    /**
     * 余额数量
     */
    private BigDecimal balance;
    /**
     * 归集数量
     */
    private BigDecimal amount;
    /**
     * 归集状态 1未归集 2归集中 3归集成功 4归集失败
     */
    private Integer status;
    /**
     * 描述
     */
    private String message;
    /**
     * 归集Hash
     */
    private String hash;
    private String createTime;
    private String updateTime;


    public void setSweepNo(String sweepNo) {
        this.sweepNo = sweepNo;
    }

    public String getSweepNo() {
        return sweepNo;
    }


    public void setSweepId(String sweepId) {
        this.sweepId = sweepId;
    }

    public String getSweepId() {
        return sweepId;
    }


    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }


    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getType() {
        return type;
    }


    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getBalance() {
        return balance;
    }


    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getAmount() {
        return amount;
    }


    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }


    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }


    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getHash() {
        return hash;
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


}