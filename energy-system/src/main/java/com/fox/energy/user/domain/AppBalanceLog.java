package com.fox.energy.user.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 用户余额记录对象 app_balance_log
 *
 * @author IEME
 * @date 2024-05-13
 */
public class AppBalanceLog {

    /**
     * $column.columnComment
     */
    private Long id;
    /**
     * 用户ID
     */
    private String userId;
    /**
     * 订单号
     */
    private String orderNo;
    /**
     * 余额
     */
    private BigDecimal beforeBalance;
    /**
     * 数量
     */
    private BigDecimal amount;
    /**
     * 余额
     */
    private BigDecimal afterBalance;
    /**
     * $column.columnComment
     */
    private String createAt;


    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getOrderNo() {
        return orderNo;
    }


    public void setBeforeBalance(BigDecimal beforeBalance) {
        this.beforeBalance = beforeBalance;
    }

    public BigDecimal getBeforeBalance() {
        return beforeBalance;
    }


    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getAmount() {
        return amount;
    }


    public BigDecimal getAfterBalance() {
        return afterBalance;
    }

    public void setAfterBalance(BigDecimal afterBalance) {
        this.afterBalance = afterBalance;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public String getCreateAt() {
        return createAt;
    }


}