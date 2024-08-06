package com.fox.energy.tron.mapper;

import com.fox.energy.tron.domain.TronAddress;

import java.util.List;

/**
 * TRON地址Mapper接口
 *
 * @author IEME
 * @date 2024-05-13
 */
public interface TronAddressMapper {
    /**
     * 查询TRON地址
     *
     * @param id TRON地址主键
     * @return TRON地址
     */
    TronAddress selectById(Long id);

    TronAddress selectByUserId(Long userId);

    TronAddress selectByAddress(String address);

    TronAddress selectNoUsed();

    /**
     * 查询TRON地址列表
     *
     * @param tronAddress TRON地址
     * @return TRON地址集合
     */
    List<TronAddress> selectList(TronAddress tronAddress);

    /**
     * 新增TRON地址
     *
     * @param tronAddress TRON地址
     * @return 结果
     */
    int insert(TronAddress tronAddress);

    /**
     * 修改TRON地址
     *
     * @param tronAddress TRON地址
     * @return 结果
     */
    int update(TronAddress tronAddress);

    /**
     * 删除TRON地址
     *
     * @param id TRON地址主键
     * @return 结果
     */
    int deleteById(Long id);

    /**
     * 批量删除TRON地址
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    int deleteByIds(Long[] ids);
}
