package com.fox.energy.user.service;

import java.util.List;

import com.fox.energy.user.domain.AppBalanceLog;

/**
 * 用户余额记录Service接口
 *
 * @author IEME
 * @date 2024-05-13
 */
public interface IAppBalanceLogService {
    /**
     * 查询用户余额记录
     *
     * @param id 用户余额记录主键
     * @return 用户余额记录
     */
        AppBalanceLog selectById(Long id);

    /**
     * 查询用户余额记录列表
     *
     * @param appBalanceLog 用户余额记录
     * @return 用户余额记录集合
     */
    List<AppBalanceLog> selectList(AppBalanceLog appBalanceLog);

    /**
     * 新增用户余额记录
     *
     * @param appBalanceLog 用户余额记录
     * @return 结果
     */
    int insert(AppBalanceLog appBalanceLog);

    /**
     * 修改用户余额记录
     *
     * @param appBalanceLog 用户余额记录
     * @return 结果
     */
    int update(AppBalanceLog appBalanceLog);

    /**
     * 批量删除用户余额记录
     *
     * @param ids 需要删除的用户余额记录主键集合
     * @return 结果
     */
    int deleteByIds(Long[] ids);

    /**
     * 删除用户余额记录信息
     *
     * @param id 用户余额记录主键
     * @return 结果
     */
    int deleteById(Long id);
}
