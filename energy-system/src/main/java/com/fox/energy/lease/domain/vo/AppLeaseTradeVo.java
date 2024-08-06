package com.fox.energy.lease.domain.vo;

import java.math.BigDecimal;

/**
 * 能量租用订单对象 app_lease_trade
 *
 * @author IEME
 * @date 2024-04-24
 */
public class AppLeaseTradeVo {

    /**
     * 订单号
     */
    private String orderNo;
    /**
     * 接收方地址
     */
    private String receiveAddress;
    /**
     * 租赁资源数量
     */
    private Long resourceValue;
    /**
     * 租赁时间类型 1小时 2天
     */
    private Integer leaseDurationType;
    /**
     * 租赁时长
     */
    private Integer leaseDuration;
    /**
     * 支付金额
     */
    private BigDecimal payAmount;
    /**
     * 支付状态 1未支付 2支付成功 3支付取消 4已退款
     */
    private Integer payStatus;
    /**
     * 代理时间
     */
    private String leaseTime;
    /**
     * 租赁状态 1未租赁 2租赁中 3租赁成功 4租赁失败
     */
    private Integer leaseStatus;
    /**
     * 到期时间
     */
    private String expireTime;
    /**
     * 代理Hash
     */
    private String frozenTxId;
    /**
     * 回收Hash
     */
    private String unfreezeTxId;


    private String createTime;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getReceiveAddress() {
        return receiveAddress;
    }

    public void setReceiveAddress(String receiveAddress) {
        this.receiveAddress = receiveAddress;
    }

    public Long getResourceValue() {
        return resourceValue;
    }

    public void setResourceValue(Long resourceValue) {
        this.resourceValue = resourceValue;
    }

    public Integer getLeaseDurationType() {
        return leaseDurationType;
    }

    public void setLeaseDurationType(Integer leaseDurationType) {
        this.leaseDurationType = leaseDurationType;
    }

    public Integer getLeaseDuration() {
        return leaseDuration;
    }

    public void setLeaseDuration(Integer leaseDuration) {
        this.leaseDuration = leaseDuration;
    }

    public BigDecimal getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(BigDecimal payAmount) {
        this.payAmount = payAmount;
    }

    public Integer getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }


    public Integer getLeaseStatus() {
        return leaseStatus;
    }

    public void setLeaseStatus(Integer leaseStatus) {
        this.leaseStatus = leaseStatus;
    }


    public String getFrozenTxId() {
        return frozenTxId;
    }

    public void setFrozenTxId(String frozenTxId) {
        this.frozenTxId = frozenTxId;
    }

    public String getUnfreezeTxId() {
        return unfreezeTxId;
    }

    public void setUnfreezeTxId(String unfreezeTxId) {
        this.unfreezeTxId = unfreezeTxId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getLeaseTime() {
        return leaseTime;
    }

    public void setLeaseTime(String leaseTime) {
        this.leaseTime = leaseTime;
    }

    public String getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(String expireTime) {
        this.expireTime = expireTime;
    }
}