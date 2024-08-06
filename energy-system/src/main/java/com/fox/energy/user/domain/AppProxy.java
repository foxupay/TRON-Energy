package com.fox.energy.user.domain;

import java.math.BigDecimal;

/**
 * 代理利率对象 app_proxy
 *
 * @author IEME
 * @date 2024-07-15
 */
public class AppProxy
{

    /** $column.columnComment */
    private Integer id;
    /** 用户编号 */
    private Integer userId;
    /** 利率 */
    private BigDecimal rate;
    private String updateTime;



    public void setId(Integer id)
            {
            this.id = id;
            }

    public Integer getId()
            {
            return id;
            }


    public void setUserId(Integer userId)
            {
            this.userId = userId;
            }

    public Integer getUserId()
            {
            return userId;
            }


    public void setRate(BigDecimal rate)
            {
            this.rate = rate;
            }

    public BigDecimal getRate()
            {
            return rate;
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