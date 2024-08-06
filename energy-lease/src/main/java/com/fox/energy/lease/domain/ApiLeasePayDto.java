package com.fox.energy.lease.domain;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class ApiLeasePayDto {
    @NotBlank(message = "邮箱账户不能为空")
    private String username;
    private String password;

    private String apiKey;
    @Min(value = 1, message = "租赁资源数量必须大于0")
    @NotNull(message = "租赁资源数量不能为空")
    private Long amount;
    @NotBlank(message = "租赁时长不能为空")
    private String time;
    @NotBlank(message = "接收资源地址不能为空")
    private String address;

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
