package com.fox.energy.sweep.service;

import java.util.List;

import com.fox.energy.sweep.domain.TronSweepLog;

/**
 * 归集记录Service接口
 *
 * @author IEME
 * @date 2024-05-16
 */
public interface ITronSweepLogService {
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

    int insertBatch(List<TronSweepLog> tronSweepLog);

    /**
     * 修改归集记录
     *
     * @param tronSweepLog 归集记录
     * @return 结果
     */
    int update(TronSweepLog tronSweepLog);

    /**
     * 批量删除归集记录
     *
     * @param ids 需要删除的归集记录主键集合
     * @return 结果
     */
    int deleteByIds(Long[] ids);

    /**
     * 删除归集记录信息
     *
     * @param id 归集记录主键
     * @return 结果
     */
    int deleteById(Long id);
}
