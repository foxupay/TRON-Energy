package com.fox.energy.app.service;

import java.util.List;

import com.fox.energy.app.domain.AppApiKey;

/**
 * 接口秘钥Service接口
 *
 * @author IEME
 * @date 2024-07-24
 */
public interface IAppApiKeyService {
    /**
     * 查询接口秘钥
     *
     * @param id 接口秘钥主键
     * @return 接口秘钥
     */
    AppApiKey selectById(Integer id);

    AppApiKey selectByApiKey(String apiKey);

    /**
     * 查询接口秘钥列表
     *
     * @param appApiKey 接口秘钥
     * @return 接口秘钥集合
     */
    List<AppApiKey> selectList(AppApiKey appApiKey);

    /**
     * 新增接口秘钥
     *
     * @param appApiKey 接口秘钥
     * @return 结果
     */
    int insert(AppApiKey appApiKey);

    /**
     * 修改接口秘钥
     *
     * @param appApiKey 接口秘钥
     * @return 结果
     */
    int update(AppApiKey appApiKey);

    /**
     * 批量删除接口秘钥
     *
     * @param ids 需要删除的接口秘钥主键集合
     * @return 结果
     */
    int deleteByIds(Integer[] ids);

    /**
     * 删除接口秘钥信息
     *
     * @param id 接口秘钥主键
     * @return 结果
     */
    int deleteById(Integer id);
}
