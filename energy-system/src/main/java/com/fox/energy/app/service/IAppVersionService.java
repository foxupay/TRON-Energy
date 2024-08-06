package com.fox.energy.app.service;

import java.util.List;

import com.fox.energy.app.domain.AppVersion;

/**
 * APP版本Service接口
 *
 * @author IEME
 * @date 2024-05-27
 */
public interface IAppVersionService {
    /**
     * 查询APP版本
     *
     * @param id APP版本主键
     * @return APP版本
     */
        AppVersion selectById(Integer id);

    /**
     * 查询APP版本列表
     *
     * @param appVersion APP版本
     * @return APP版本集合
     */
    List<AppVersion> selectList(AppVersion appVersion);

    /**
     * 新增APP版本
     *
     * @param appVersion APP版本
     * @return 结果
     */
    int insert(AppVersion appVersion);

    /**
     * 修改APP版本
     *
     * @param appVersion APP版本
     * @return 结果
     */
    int update(AppVersion appVersion);

    /**
     * 批量删除APP版本
     *
     * @param ids 需要删除的APP版本主键集合
     * @return 结果
     */
    int deleteByIds(Integer[] ids);

    /**
     * 删除APP版本信息
     *
     * @param id APP版本主键
     * @return 结果
     */
    int deleteById(Integer id);
}
