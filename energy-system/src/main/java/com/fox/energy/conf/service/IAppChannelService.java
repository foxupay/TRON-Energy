package com.fox.energy.conf.service;

import java.util.List;

import com.fox.energy.conf.domain.AppChannel;

/**
 * 能量通道Service接口
 *
 * @author IEME
 * @date 2024-07-16
 */
public interface IAppChannelService {
    /**
     * 查询能量通道
     *
     * @param id 能量通道主键
     * @return 能量通道
     */
    AppChannel selectById(Long id);

    AppChannel selectEnable();

    AppChannel selectByCode(String code);

    /**
     * 查询能量通道列表
     *
     * @param appChannel 能量通道
     * @return 能量通道集合
     */
    List<AppChannel> selectList(AppChannel appChannel);

    /**
     * 新增能量通道
     *
     * @param appChannel 能量通道
     * @return 结果
     */
    int insert(AppChannel appChannel);

    /**
     * 修改能量通道
     *
     * @param appChannel 能量通道
     * @return 结果
     */
    int update(AppChannel appChannel);

    /**
     * 批量删除能量通道
     *
     * @param ids 需要删除的能量通道主键集合
     * @return 结果
     */
    int deleteByIds(Long[] ids);

    /**
     * 删除能量通道信息
     *
     * @param id 能量通道主键
     * @return 结果
     */
    int deleteById(Long id);
}
