package com.fox.energy.sweep.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import cn.hutool.core.date.DateUtil;

import com.fox.energy.sweep.mapper.TronSweepLogMapper;
import com.fox.energy.sweep.domain.TronSweepLog;
import com.fox.energy.sweep.service.ITronSweepLogService;

/**
 * 归集记录Service业务层处理
 *
 * @author IEME
 * @date 2024-05-16
 */
@Service
public class TronSweepLogServiceImpl implements ITronSweepLogService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private TronSweepLogMapper tronSweepLogMapper;

    @Override
    public TronSweepLog selectById(Long id) {
        return tronSweepLogMapper.selectById(id);
    }

    @Override
    public TronSweepLog selectBySweepId(String sweepId) {
        return tronSweepLogMapper.selectBySweepId(sweepId);
    }

    @Override
    public List<TronSweepLog> selectList(TronSweepLog tronSweepLog) {
        return tronSweepLogMapper.selectList(tronSweepLog);
    }

    @Override
    public int insert(TronSweepLog tronSweepLog) {
        tronSweepLog.setCreateTime(DateUtil.now());
        tronSweepLog.setUpdateTime(DateUtil.now());
        return tronSweepLogMapper.insert(tronSweepLog);
    }

    @Override
    public int insertBatch(List<TronSweepLog> logs) {
        return tronSweepLogMapper.insertBatch(logs);
    }

    @Override
    public int update(TronSweepLog tronSweepLog) {
        tronSweepLog.setUpdateTime(DateUtil.now());
        return tronSweepLogMapper.update(tronSweepLog);
    }

    @Override
    public int deleteByIds(Long[] ids) {
        return tronSweepLogMapper.deleteByIds(ids);
    }

    @Override
    public int deleteById(Long id) {
        return tronSweepLogMapper.deleteById(id);
    }

}
