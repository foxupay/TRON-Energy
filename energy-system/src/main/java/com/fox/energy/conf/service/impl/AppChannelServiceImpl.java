package com.fox.energy.conf.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import cn.hutool.core.date.DateUtil;

import com.fox.energy.conf.mapper.AppChannelMapper;
import com.fox.energy.conf.domain.AppChannel;
import com.fox.energy.conf.service.IAppChannelService;

/**
 * 能量通道Service业务层处理
 *
 * @author IEME
 * @date 2024-07-16
 */
@Service
public class AppChannelServiceImpl implements IAppChannelService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private AppChannelMapper appChannelMapper;

    @Override
    public AppChannel selectById(Long id) {
        return appChannelMapper.selectById(id);
    }

    @Override
    public AppChannel selectEnable() {
        return appChannelMapper.selectEnable();
    }

    @Override
    public AppChannel selectByCode(String code) {
        return appChannelMapper.selectByCode(code);
    }

    @Override
    public List<AppChannel> selectList(AppChannel appChannel) {
        return appChannelMapper.selectList(appChannel);
    }

    @Override
    public int insert(AppChannel appChannel) {
        appChannel.setCreateTime(DateUtil.now());
        appChannel.setUpdateTime(DateUtil.now());
        return appChannelMapper.insert(appChannel);
    }

    @Override
    public int update(AppChannel appChannel) {
        appChannel.setUpdateTime(DateUtil.now());
        return appChannelMapper.update(appChannel);
    }

    @Override
    public int deleteByIds(Long[] ids) {
        return appChannelMapper.deleteByIds(ids);
    }

    @Override
    public int deleteById(Long id) {
        return appChannelMapper.deleteById(id);
    }

}
