package com.fox.energy.user.mapper;

import java.math.BigDecimal;
import java.util.List;

import com.fox.energy.user.domain.AppBalance;
import org.apache.ibatis.annotations.Param;

/**
 * 用户余额Mapper接口
 *
 * @author IEME
 * @date 2024-04-30
 */
public interface AppBalanceMapper {
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

    int change(@Param("userId") Long userId, @Param("amount") BigDecimal amount);

    /**
     * 删除用户余额
     *
     * @param id 用户余额主键
     * @return 结果
     */
    int deleteById(Long id);

    /**
     * 批量删除用户余额
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    int deleteByIds(Long[] ids);
}
