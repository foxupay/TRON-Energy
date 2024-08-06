package com.fox.energy.conf.domain;


/**
 * 能量通道对象 app_channel
 *
 * @author IEME
 * @date 2024-07-16
 */
public class AppChannel {
    private Long id;
    /**
     * 通道名称
     */
    private String channelName;
    /**
     * 通道标识
     */
    private String channelCode;
    /**
     * 应用ID
     */
    private String appId;
    /**
     * 应用Key
     */
    private String appKey;
    /**
     * 应用秘钥
     */
    private String appPem;
    /**
     * 是否启用 1是 2否
     */
    private Integer state;
    private String createTime;
    private String updateTime;


    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }


    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getChannelName() {
        return channelName;
    }


    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }

    public String getChannelCode() {
        return channelCode;
    }


    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppId() {
        return appId;
    }


    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getAppKey() {
        return appKey;
    }


    public void setAppPem(String appPem) {
        this.appPem = appPem;
    }

    public String getAppPem() {
        return appPem;
    }


    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getState() {
        return state;
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