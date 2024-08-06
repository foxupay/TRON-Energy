package com.fox.energy.sweep.domain;


/**
 * 归集批次对象 tron_sweep
 *
 * @author IEME
 * @date 2024-05-16
 */
public class TronSweep {

    /**
     * $column.columnComment
     */
    private Long id;
    /**
     * 归集批次号
     */
    private String sweepNo;
    /**
     * 名称，默认时间
     */
    private String title;
    private Integer type;

    private Integer minAmount;
    /**
     * 归集地址数量
     */
    private Integer addressNumber;
    /**
     * 实际归集数量
     */
    private Integer sweepNumber;
    /**
     * 归集成功数量
     */
    private Integer successNumber;
    /**
     * 归集状态 1未导入 2未处理 3处理中 4处理完成
     */
    private Integer status;
    private String createTime;
    private String updateTime;


    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }


    public void setSweepNo(String sweepNo) {
        this.sweepNo = sweepNo;
    }

    public String getSweepNo() {
        return sweepNo;
    }


    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }


    public void setAddressNumber(Integer addressNumber) {
        this.addressNumber = addressNumber;
    }

    public Integer getAddressNumber() {
        return addressNumber;
    }


    public void setSweepNumber(Integer sweepNumber) {
        this.sweepNumber = sweepNumber;
    }

    public Integer getSweepNumber() {
        return sweepNumber;
    }


    public void setSuccessNumber(Integer successNumber) {
        this.successNumber = successNumber;
    }

    public Integer getSuccessNumber() {
        return successNumber;
    }


    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return status;
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getMinAmount() {
        return minAmount;
    }

    public void setMinAmount(Integer minAmount) {
        this.minAmount = minAmount;
    }
}