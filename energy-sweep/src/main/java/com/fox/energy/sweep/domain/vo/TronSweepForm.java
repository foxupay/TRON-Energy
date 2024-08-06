package com.fox.energy.sweep.domain.vo;


/**
 * 归集批次对象 tron_sweep
 *
 * @author IEME
 * @date 2024-05-16
 */
public class TronSweepForm {

    private Integer type;
    /**
     * 归集地址数量
     */
    private Integer number;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }
}