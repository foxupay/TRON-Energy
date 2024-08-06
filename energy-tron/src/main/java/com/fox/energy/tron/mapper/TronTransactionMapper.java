package com.fox.energy.tron.mapper;

import com.fox.energy.tron.domain.TronTransaction;

import java.util.List;

/**
 * TRON交易Mapper接口
 *
 * @author IEME
 * @date 2024-05-11
 */
public interface TronTransactionMapper {
    /**
     * 查询TRON交易
     *
     * @param id TRON交易主键
     * @return TRON交易
     */
    TronTransaction selectById(Long id);

    TronTransaction selectByHash(String hash);

    /**
     * 查询TRON交易列表
     *
     * @param tronTransaction TRON交易
     * @return TRON交易集合
     */
    List<TronTransaction> selectList(TronTransaction tronTransaction);

    /**
     * 新增TRON交易
     *
     * @param tronTransaction TRON交易
     * @return 结果
     */
    int insert(TronTransaction tronTransaction);

    /**
     * 修改TRON交易
     *
     * @param tronTransaction TRON交易
     * @return 结果
     */
    int update(TronTransaction tronTransaction);

    /**
     * 删除TRON交易
     *
     * @param id TRON交易主键
     * @return 结果
     */
    int deleteById(Long id);

    /**
     * 批量删除TRON交易
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    int deleteByIds(Long[] ids);
}
