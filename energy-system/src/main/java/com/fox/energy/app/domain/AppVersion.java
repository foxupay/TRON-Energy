package com.fox.energy.app.domain;


/**
 * APP版本对象 app_version
 *
 * @author IEME
 * @date 2024-05-27
 */
public class AppVersion {

    /**
     * $column.columnComment
     */
    private Integer id;
    /**
     * 软件类型 1安卓 2IOS
     */
    private Integer type;
    /**
     * 版本号 A.B.C A主版本号 B次版本号 C修订号
     */
    private String version;
    /**
     * 版本更新介绍
     */
    private String description;
    /**
     * 下载地址
     */
    private String url;


    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }


    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getType() {
        return type;
    }


    public void setVersion(String version) {
        this.version = version;
    }

    public String getVersion() {
        return version;
    }


    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }


    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }


}