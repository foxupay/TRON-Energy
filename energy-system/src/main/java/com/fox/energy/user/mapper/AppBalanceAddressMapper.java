package com.fox.energy.user.mapper;

import java.math.BigDecimal;
import java.util.List;

import com.fox.energy.user.domain.AppBalance;
import com.fox.energy.user.domain.AppBalanceAddress;
import org.apache.ibatis.annotations.Param;

/**
 * 地址余额Mapper接口
 *
 * @author IEME
 * @date 2024-05-14
 */
public interface AppBalanceAddressMapper {
    /**
     * 查询地址余额
     *
     * @param id 地址余额主键
     * @return 地址余额
     */
    AppBalanceAddress selectById(Long id);

    AppBalanceAddress selectByAddress(String address);

    /**
     * 查询地址余额列表
     *
     * @param appBalanceAddress 地址余额
     * @return 地址余额集合
     */
    List<AppBalanceAddress> selectList(AppBalanceAddress appBalanceAddress);

    /**
     * 新增地址余额
     *
     * @param appBalanceAddress 地址余额
     * @return 结果
     */
    int insert(AppBalanceAddress appBalanceAddress);

    /**
     * 修改地址余额
     *
     * @param appBalanceAddress 地址余额
     * @return 结果
     */
    int update(AppBalanceAddress appBalanceAddress);

    int change(@Param("address") String address, @Param("amount") BigDecimal amount);

    /**
     * 删除地址余额
     *
     * @param id 地址余额主键
     * @return 结果
     */
    int deleteById(Long id);

    /**
     * 批量删除地址余额
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    int deleteByIds(Long[] ids);
}
