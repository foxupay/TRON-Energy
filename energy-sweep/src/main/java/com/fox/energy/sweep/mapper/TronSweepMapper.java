package com.fox.energy.sweep.mapper;

import java.util.List;

import com.fox.energy.sweep.domain.TronSweep;

/**
 * 归集批次Mapper接口
 *
 * @author IEME
 * @date 2024-05-16
 */
public interface TronSweepMapper {
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
     * 删除归集批次
     *
     * @param id 归集批次主键
     * @return 结果
     */
    int deleteById(Long id);

    /**
     * 批量删除归集批次
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    int deleteByIds(Long[] ids);
}
