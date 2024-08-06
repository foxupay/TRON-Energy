package com.fox.energy.tron.service.impl;

import cn.hutool.core.date.DateUtil;
import com.fox.energy.tron.domain.TronAddress;
import com.fox.energy.tron.mapper.TronAddressMapper;
import com.fox.energy.tron.service.ITronAddressService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * TRON地址Service业务层处理
 *
 * @author IEME
 * @date 2024-05-13
 */
@Service
public class TronAddressServiceImpl implements ITronAddressService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private TronAddressMapper tronAddressMapper;

    @Override
    public TronAddress selectById(Long id) {
        return tronAddressMapper.selectById(id);
    }

    @Override
    public TronAddress selectByUserId(Long userId) {
        return tronAddressMapper.selectByUserId(userId);
    }

    @Override
    public TronAddress selectByAddress(String address) {
        return tronAddressMapper.selectByAddress(address);
    }

    @Override
    public TronAddress selectNoUsed() {
        return tronAddressMapper.selectNoUsed();
    }

    @Override
    public List<TronAddress> selectList(TronAddress tronAddress) {
        return tronAddressMapper.selectList(tronAddress);
    }

    @Override
    public int insert(TronAddress tronAddress) {
        tronAddress.setCreateTime(DateUtil.now());
        tronAddress.setUpdateTime(DateUtil.now());
        return tronAddressMapper.insert(tronAddress);
    }

    @Override
    public int update(TronAddress tronAddress) {
        return tronAddressMapper.update(tronAddress);
    }

    @Override
    public int deleteByIds(Long[] ids) {
        return tronAddressMapper.deleteByIds(ids);
    }

    @Override
    public int deleteById(Long id) {
        return tronAddressMapper.deleteById(id);
    }


}
