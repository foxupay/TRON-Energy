package com.fox.energy.user.service;

import java.math.BigDecimal;
import java.util.List;

import com.fox.energy.user.domain.AppBalance;

/**
 * 用户余额Service接口
 *
 * @author IEME
 * @date 2024-04-30
 */
public interface IAppBalanceService {
    /**
     * 查询用户余额
     *
     * @param id 用户余额主键
     * @return 用户余额
     */
    AppBalance selectById(Long id);

    AppBalance selectByUserId(Long userId);

    /**
     * 查询用户余额列表
     *
     * @param appBalance 用户余额
     * @return 用户余额集合
     */
    List<AppBalance> selectList(AppBalance appBalance);

    /**
     * 新增用户余额
     *
     * @param appBalance 用户余额
     * @return 结果
     */
    int insert(AppBalance appBalance);

    /**
     * 修改用户余额
     *
     * @param appBalance 用户余额
     * @return 结果
     */
    int update(AppBalance appBalance);

    int change(Long userId, BigDecimal amount);

    /**
     * 批量删除用户余额
     *
     * @param ids 需要删除的用户余额主键集合
     * @return 结果
     */
    int deleteByIds(Long[] ids);

    /**
     * 删除用户余额信息
     *
     * @param id 用户余额主键
     * @return 结果
     */
    int deleteById(Long id);

    void changeBalance(Long userId, String orderNo, BigDecimal amount);
}
