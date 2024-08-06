package com.fox.energy.common.core.domain.tron;

import java.math.BigDecimal;

public class EnergyPriceConfig {

    /**
     * 最低购买能量
     */
    private Long min;
    /**
     * 最高购买能量
     */
    private Long max;
    /**
     * 是否可以下单
     */
    private boolean canPay;
    /**
     * 1小时单价，sun
     */
    private BigDecimal h1;
    /**
     * 1天单价，sun
     */
    private BigDecimal h24;
    /**
     * 3天单价，sun
     */
    private BigDecimal h72;
    /**
     * 其他天数单价，sun
     */
    private BigDecimal other;

    public boolean isCanPay() {
        return canPay;
    }

    public void setCanPay(boolean canPay) {
        this.canPay = canPay;
    }

    public Long getMin() {
        return min;
    }

    public void setMin(Long min) {
        this.min = min;
    }

    public Long getMax() {
        return max;
    }

    public void setMax(Long max) {
        this.max = max;
    }

    public BigDecimal getH1() {
        return h1;
    }

    public void setH1(BigDecimal h1) {
        this.h1 = h1;
    }

    public BigDecimal getH24() {
        return h24;
    }

    public void setH24(BigDecimal h24) {
        this.h24 = h24;
    }

    public BigDecimal getH72() {
        return h72;
    }

    public void setH72(BigDecimal h72) {
        this.h72 = h72;
    }

    public BigDecimal getOther() {
        return other;
    }

    public void setOther(BigDecimal other) {
        this.other = other;
    }
}
