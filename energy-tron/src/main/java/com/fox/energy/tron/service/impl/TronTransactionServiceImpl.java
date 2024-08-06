package com.fox.energy.tron.service.impl;

import cn.hutool.core.date.DateUtil;
import com.fox.energy.tron.domain.TronTransaction;
import com.fox.energy.tron.mapper.TronTransactionMapper;
import com.fox.energy.tron.service.ITronTransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * TRON交易Service业务层处理
 *
 * @author IEME
 * @date 2024-05-11
 */
@Service
public class TronTransactionServiceImpl implements ITronTransactionService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private TronTransactionMapper tronTransactionMapper;

    @Override
    public TronTransaction selectById(Long id) {
        return tronTransactionMapper.selectById(id);
    }

    @Override
    public TronTransaction selectByHash(String hash) {
        return tronTransactionMapper.selectByHash(hash);
    }

    @Override
    public List<TronTransaction> selectList(TronTransaction tronTransaction) {
        return tronTransactionMapper.selectList(tronTransaction);
    }

    @Override
    public int insert(TronTransaction tronTransaction) {
        tronTransaction.setUpdateTime(DateUtil.now());
        return tronTransactionMapper.insert(tronTransaction);
    }

    @Override
    public int update(TronTransaction tronTransaction) {
        tronTransaction.setUpdateTime(DateUtil.now());
        return tronTransactionMapper.update(tronTransaction);
    }

    @Override
    public int deleteByIds(Long[] ids) {
        return tronTransactionMapper.deleteByIds(ids);
    }

    @Override
    public int deleteById(Long id) {
        return tronTransactionMapper.deleteById(id);
    }

}
