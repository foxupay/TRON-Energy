package com.fox.energy.lease.domain;

import java.math.BigDecimal;

/**
 * 能量租用订单对象 app_lease_trade
 *
 * @author IEME
 * @date 2024-05-15
 */
public class AppLeaseTrade {

    /**
     * $column.columnComment
     */
    private Integer id;

    /**
     * 交易通道
     */
    private String channel;
    /**
     * 用户编号
     */
    private String userId;
    /**
     * 订单号
     */
    private String orderNo;
    /**
     * 接收方地址
     */
    private String receiveAddress;
    /**
     * 租赁类型 1能量 2笔数
     */
    private Integer leaseType;
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
     * 单价
     */
    private BigDecimal priceInSun;
    /**
     * 支付TRX数量
     */
    private BigDecimal payTrx;
    /**
     * 支付USDT数量
     */
    private BigDecimal payUsdt;
    /**
     * 地址支付绑定地址
     */
    private String payAddress;
    /**
     * 地址支付Hash
     */
    private String payHash;
    /**
     * 支付时间
     */
    private String payTime;
    /**
     * 支付方式 1账户余额 2地址余额 3链上支付
     */
    private Integer payWay;
    /**
     * 支付状态 1未支付 2支付成功 3支付取消 4已退款
     */
    private Integer payStatus;
    /**
     * 代理单号
     */
    private String leaseNo;
    /**
     * 代理时间
     */
    private String leaseTime;
    /**
     * 租用状态 1未租赁 2租赁中 3租赁成功 4租赁失败
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

    private String message;
    private String createTime;
    private String updateTime;


    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }


    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getOrderNo() {
        return orderNo;
    }


    public void setReceiveAddress(String receiveAddress) {
        this.receiveAddress = receiveAddress;
    }

    public String getReceiveAddress() {
        return receiveAddress;
    }


    public void setLeaseType(Integer leaseType) {
        this.leaseType = leaseType;
    }

    public Integer getLeaseType() {
        return leaseType;
    }


    public void setResourceValue(Long resourceValue) {
        this.resourceValue = resourceValue;
    }

    public Long getResourceValue() {
        return resourceValue;
    }


    public void setLeaseDurationType(Integer leaseDurationType) {
        this.leaseDurationType = leaseDurationType;
    }

    public Integer getLeaseDurationType() {
        return leaseDurationType;
    }


    public void setLeaseDuration(Integer leaseDuration) {
        this.leaseDuration = leaseDuration;
    }

    public Integer getLeaseDuration() {
        return leaseDuration;
    }


    public void setPriceInSun(BigDecimal priceInSun) {
        this.priceInSun = priceInSun;
    }

    public BigDecimal getPriceInSun() {
        return priceInSun;
    }


    public void setPayTrx(BigDecimal payTrx) {
        this.payTrx = payTrx;
    }

    public BigDecimal getPayTrx() {
        return payTrx;
    }


    public void setPayUsdt(BigDecimal payUsdt) {
        this.payUsdt = payUsdt;
    }

    public BigDecimal getPayUsdt() {
        return payUsdt;
    }


    public void setPayAddress(String payAddress) {
        this.payAddress = payAddress;
    }

    public String getPayAddress() {
        return payAddress;
    }


    public void setPayHash(String payHash) {
        this.payHash = payHash;
    }

    public String getPayHash() {
        return payHash;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
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

    public Integer getPayWay() {
        return payWay;
    }

    public void setPayWay(Integer payWay) {
        this.payWay = payWay;
    }

    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }

    public Integer getPayStatus() {
        return payStatus;
    }


    public void setLeaseNo(String leaseNo) {
        this.leaseNo = leaseNo;
    }

    public String getLeaseNo() {
        return leaseNo;
    }


    public void setLeaseStatus(Integer leaseStatus) {
        this.leaseStatus = leaseStatus;
    }

    public Integer getLeaseStatus() {
        return leaseStatus;
    }


    public void setFrozenTxId(String frozenTxId) {
        this.frozenTxId = frozenTxId;
    }

    public String getFrozenTxId() {
        return frozenTxId;
    }


    public void setUnfreezeTxId(String unfreezeTxId) {
        this.unfreezeTxId = unfreezeTxId;
    }

    public String getUnfreezeTxId() {
        return unfreezeTxId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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