package com.fox.energy.sweep.service;

import java.util.List;

import com.fox.energy.sweep.domain.TronSweep;

/**
 * 归集批次Service接口
 *
 * @author IEME
 * @date 2024-05-16
 */
public interface ITronSweepService {
    /**
     * 查询归集批次
     *
     * @param id 归集批次主键
     * @return 归集批次
     */
    TronSweep selectById(Long id);

    TronSweep selectBySweepNo(String sweepNo);

    /**
     * 查询归集批次列表
     *
     * @param tronSweep 归集批次
     * @return 归集批次集合
     */
    List<TronSweep> selectList(TronSweep tronSweep);

    /**
     * 新增归集批次
     *
     * @param tronSweep 归集批次
     * @return 结果
     */
    int insert(TronSweep tronSweep);

    /**
     * 修改归集批次
     *
     * @param tronSweep 归集批次
     * @return 结果
     */
    int update(TronSweep tronSweep);

    /**
     * 批量删除归集批次
     *
     * @param ids 需要删除的归集批次主键集合
     * @return 结果
     */
    int deleteByIds(Long[] ids);

    /**
     * 删除归集批次信息
     *
     * @param id 归集批次主键
     * @return 结果
     */
    int deleteById(Long id);
}
