package com.fox.energy.web.domain;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.*;

public class AppLeaseCreateForm {

    /**
     * 接收方地址
     */
    @ApiModelProperty(value = "接收方地址 ")
    @NotBlank(message = "接收方地址不能为空")
    private String receiveAddress;
    /**
     * 租赁资源数量
     */
    @ApiModelProperty(value = "租赁资源数量 ")
    @Min(value = 1, message = "租赁资源数量必须大于0")
    @NotNull(message = "租赁资源数量不能为空")
    private Long resourceValue;
    /**
     * 租赁时长 1h 1d
     */
    @ApiModelProperty(value = "租用时长  1h 1d h小时 d天")
    @NotBlank(message = "租赁时长不能为空")
    private String leaseDuration;

    @ApiModelProperty(value = "支付方式 1:地址支付 2:账户余额支付 3:地址余额支付")
    @Min(value = 1, message = "支付方式错误")
    @Max(value = 3, message = "支付方式错误")
    private Integer payWay;

    public Integer getPayWay() {
        return payWay;
    }

    public void setPayWay(Integer payWay) {
        this.payWay = payWay;
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

    public String getLeaseDuration() {
        return leaseDuration;
    }

    public void setLeaseDuration(String leaseDuration) {
        this.leaseDuration = leaseDuration;
    }
}
