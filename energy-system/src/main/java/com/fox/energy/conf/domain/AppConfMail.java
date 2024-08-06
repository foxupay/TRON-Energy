package com.fox.energy.conf.domain;


/**
 * 邮件配置对象 app_conf_mail
 *
 * @author IEME
 * @date 2024-07-22
 */
public class AppConfMail {

    /**
     * $column.columnComment
     */
    private Long id;
    /**
     * 邮件服务器的SMTP地址，可选，默认为smtp.
     */
    private String host;
    /**
     * 邮件服务器的SMTP端口，可选，默认25
     */
    private Integer port;
    /**
     * 发件人（必须正确，否则发送失败）
     */
    private String from;
    /**
     * 用户名，默认为发件人邮箱前缀
     */
    private String user;
    /**
     * 密码/授权码
     */
    private String pass;
    /**
     * 使用SSL安全连接
     */
    private Integer sslEnable;
    private String updateTime;


    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }


    public void setHost(String host) {
        this.host = host;
    }

    public String getHost() {
        return host;
    }


    public void setPort(Integer port) {
        this.port = port;
    }

    public Integer getPort() {
        return port;
    }


    public void setFrom(String from) {
        this.from = from;
    }

    public String getFrom() {
        return from;
    }


    public void setUser(String user) {
        this.user = user;
    }

    public String getUser() {
        return user;
    }


    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getPass() {
        return pass;
    }


    public void setSslEnable(Integer sslEnable) {
        this.sslEnable = sslEnable;
    }

    public Integer getSslEnable() {
        return sslEnable;
    }


    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }


}