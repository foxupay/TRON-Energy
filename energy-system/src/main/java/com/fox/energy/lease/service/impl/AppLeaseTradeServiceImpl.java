package com.fox.energy.lease.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import cn.hutool.core.date.DateUtil;

import com.fox.energy.lease.mapper.AppLeaseTradeMapper;
import com.fox.energy.lease.domain.AppLeaseTrade;
import com.fox.energy.lease.service.IAppLeaseTradeService;

/**
 * 能量租用订单Service业务层处理
 *
 * @author IEME
 * @date 2024-04-24
 */
@Service
public class AppLeaseTradeServiceImpl implements IAppLeaseTradeService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private AppLeaseTradeMapper appLeaseTradeMapper;

    @Override
    public AppLeaseTrade selectById(Integer id) {
        return appLeaseTradeMapper.selectById(id);
    }

    @Override
    public AppLeaseTrade selectByOrderNo(String orderNo) {
        return appLeaseTradeMapper.selectByOrderNo(orderNo);
    }

    @Override
    public List<AppLeaseTrade> selectList(AppLeaseTrade appLeaseTrade) {
        return appLeaseTradeMapper.selectList(appLeaseTrade);
    }

    @Override
    public int insert(AppLeaseTrade appLeaseTrade) {
        appLeaseTrade.setCreateTime(DateUtil.now());
        appLeaseTrade.setUpdateTime(DateUtil.now());
        return appLeaseTradeMapper.insert(appLeaseTrade);
    }

    @Override
    public int update(AppLeaseTrade appLeaseTrade) {
        appLeaseTrade.setUpdateTime(DateUtil.now());
        return appLeaseTradeMapper.update(appLeaseTrade);
    }

    @Override
    public int deleteByIds(Integer[] ids) {
        return appLeaseTradeMapper.deleteByIds(ids);
    }

    @Override
    public int deleteById(Integer id) {
        return appLeaseTradeMapper.deleteById(id);
    }

}
