package com.fox.energy.sweep.mapper;

import java.util.List;

import com.fox.energy.sweep.domain.TronSweepLog;

/**
 * 归集记录Mapper接口
 *
 * @author IEME
 * @date 2024-05-16
 */
public interface TronSweepLogMapper {
    /**
     * 查询归集记录
     *
     * @param id 归集记录主键
     * @return 归集记录
     */
    TronSweepLog selectById(Long id);

    TronSweepLog selectBySweepId(String sweepId);

    /**
     * 查询归集记录列表
     *
     * @param tronSweepLog 归集记录
     * @return 归集记录集合
     */
    List<TronSweepLog> selectList(TronSweepLog tronSweepLog);

    /**
     * 新增归集记录
     *
     * @param tronSweepLog 归集记录
     * @return 结果
     */
    int insert(TronSweepLog tronSweepLog);

    int insertBatch(List<TronSweepLog> list);

    /**
     * 修改归集记录
     *
     * @param tronSweepLog 归集记录
     * @return 结果
     */
    int update(TronSweepLog tronSweepLog);

    /**
     * 删除归集记录
     *
     * @param id 归集记录主键
     * @return 结果
     */
    int deleteById(Long id);

    /**
     * 批量删除归集记录
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    int deleteByIds(Long[] ids);
}