package com.fox.energy.user.domain;

import java.math.BigDecimal;

/**
 * 用户余额对象 app_balance
 *
 * @author IEME
 * @date 2024-04-30
 */
public class AppBalance
{

    /** $column.columnComment */
    private Long id;
    /** 用户ID */
    private Long userId;
    /** 余额 */
    private BigDecimal balance;
    private String createTime;
    private String updateTime;



    public void setId(Long id)
            {
            this.id = id;
            }

    public Long getId()
            {
            return id;
            }


    public void setUserId(Long userId)
            {
            this.userId = userId;
            }

    public Long getUserId()
            {
            return userId;
            }


    public void setBalance(BigDecimal balance)
            {
            this.balance = balance;
            }

    public BigDecimal getBalance()
            {
            return balance;
            }


    public void setCreateTime(String createTime)
            {
            this.createTime = createTime;
            }

    public String getCreateTime()
            {
            return createTime;
            }


    public void setUpdateTime(String updateTime)
            {
            this.updateTime = updateTime;
            }

    public String getUpdateTime()
            {
            return updateTime;
            }


        }