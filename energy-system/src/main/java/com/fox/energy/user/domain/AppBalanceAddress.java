package com.fox.energy.user.domain;

import java.math.BigDecimal;

/**
 * 地址余额对象 app_balance_address
 *
 * @author IEME
 * @date 2024-05-14
 */
public class AppBalanceAddress {

    /**
     * $column.columnComment
     */
    private Long id;
    /**
     * 地址
     */
    private String address;
    /**
     * 余额
     */
    private BigDecimal balance;
    private String createTime;
    private String updateTime;


    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }


    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }


    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getBalance() {
        return balance;
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