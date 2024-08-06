package com.fox.energy.sweep.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import cn.hutool.core.date.DateUtil;

import com.fox.energy.sweep.mapper.TronSweepMapper;
import com.fox.energy.sweep.domain.TronSweep;
import com.fox.energy.sweep.service.ITronSweepService;

/**
 * 归集批次Service业务层处理
 *
 * @author IEME
 * @date 2024-05-16
 */
@Service
public class TronSweepServiceImpl implements ITronSweepService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private TronSweepMapper tronSweepMapper;

    @Override
    public TronSweep selectById(Long id) {
        return tronSweepMapper.selectById(id);
    }

    @Override
    public TronSweep selectBySweepNo(String sweepNo) {
        return tronSweepMapper.selectBySweepNo(sweepNo);
    }

    @Override
    public List<TronSweep> selectList(TronSweep tronSweep) {
        return tronSweepMapper.selectList(tronSweep);
    }

    @Override
    public int insert(TronSweep tronSweep) {
        tronSweep.setCreateTime(DateUtil.now());
        tronSweep.setUpdateTime(DateUtil.now());
        return tronSweepMapper.insert(tronSweep);
    }

    @Override
    public int update(TronSweep tronSweep) {
        tronSweep.setUpdateTime(DateUtil.now());
        return tronSweepMapper.update(tronSweep);
    }

    @Override
    public int deleteByIds(Long[] ids) {
        return tronSweepMapper.deleteByIds(ids);
    }

    @Override
    public int deleteById(Long id) {
        return tronSweepMapper.deleteById(id);
    }

}
