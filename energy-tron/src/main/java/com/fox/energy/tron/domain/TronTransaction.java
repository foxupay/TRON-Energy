package com.fox.energy.tron.domain;

import java.math.BigDecimal;

/**
 * TRON交易对象 tron_transaction
 *
 * @author IEME
 * @date 2024-05-11
 */
public class TronTransaction {

    /**
     *
     */
    private Long id;
    /**
     * 发送者
     */
    private String fromAddress;
    /**
     * 接收者
     */
    private String toAddress;
    /**
     * 数量
     */
    private BigDecimal amount;
    /**
     * 代币类型 1TRX 2USDT
     */
    private Integer type;
    /**
     *
     */
    private String hash;
    /**
     * 状态 1支付中 2成功 3失败
     */
    private Integer status;
    /**
     * 是否已处理 1待同步 2已同步，待处理 3已处理 4待人工确认 5交易无效
     */
    private Integer handle;

    private String message;
    private String updateTime;


    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }


    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }

    public String getFromAddress() {
        return fromAddress;
    }


    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }

    public String getToAddress() {
        return toAddress;
    }


    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getAmount() {
        return amount;
    }


    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getType() {
        return type;
    }


    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getHash() {
        return hash;
    }


    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }


    public void setHandle(Integer handle) {
        this.handle = handle;
    }

    public Integer getHandle() {
        return handle;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }


}