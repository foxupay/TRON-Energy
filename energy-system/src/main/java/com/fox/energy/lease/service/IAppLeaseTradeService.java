package com.fox.energy.lease.service;

import java.util.List;

import com.fox.energy.lease.domain.AppLeaseTrade;

/**
 * 能量租用订单Service接口
 *
 * @author IEME
 * @date 2024-04-24
 */
public interface IAppLeaseTradeService {
    /**
     * 查询能量租用订单
     *
     * @param id 能量租用订单主键
     * @return 能量租用订单
     */
    AppLeaseTrade selectById(Integer id);

    AppLeaseTrade selectByOrderNo(String orderNo);

    /**
     * 查询能量租用订单列表
     *
     * @param appLeaseTrade 能量租用订单
     * @return 能量租用订单集合
     */
    List<AppLeaseTrade> selectList(AppLeaseTrade appLeaseTrade);

    /**
     * 新增能量租用订单
     *
     * @param appLeaseTrade 能量租用订单
     * @return 结果
     */
    int insert(AppLeaseTrade appLeaseTrade);

    /**
     * 修改能量租用订单
     *
     * @param appLeaseTrade 能量租用订单
     * @return 结果
     */
    int update(AppLeaseTrade appLeaseTrade);

    /**
     * 批量删除能量租用订单
     *
     * @param ids 需要删除的能量租用订单主键集合
     * @return 结果
     */
    int deleteByIds(Integer[] ids);

    /**
     * 删除能量租用订单信息
     *
     * @param id 能量租用订单主键
     * @return 结果
     */
    int deleteById(Integer id);
}
