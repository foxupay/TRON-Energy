package com.fox.energy.app.domain;


/**
 * 接口秘钥对象 app_api_key
 *
 * @author IEME
 * @date 2024-07-24
 */
public class AppApiKey
{

    /**  */
    private Integer id;
    /** 用户编号 */
    private Integer userId;
    /** 秘钥名称 */
    private String name;
    /** 秘钥 */
    private String apiKey;
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


    public void setName(String name)
            {
            this.name = name;
            }

    public String getName()
            {
            return name;
            }


    public void setApiKey(String apiKey)
            {
            this.apiKey = apiKey;
            }

    public String getApiKey()
            {
            return apiKey;
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