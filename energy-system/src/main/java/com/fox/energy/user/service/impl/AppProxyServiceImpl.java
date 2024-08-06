package com.fox.energy.user.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import cn.hutool.core.date.DateUtil;

import com.fox.energy.user.mapper.AppProxyMapper;
import com.fox.energy.user.domain.AppProxy;
import com.fox.energy.user.service.IAppProxyService;

/**
 * 代理利率Service业务层处理
 *
 * @author IEME
 * @date 2024-07-15
 */
@Service
public class AppProxyServiceImpl implements IAppProxyService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private AppProxyMapper appProxyMapper;

    @Override
    public AppProxy selectById(Integer id) {
        return appProxyMapper.selectById(id);
    }

    @Override
    public AppProxy selectByUserId(Integer userId) {
        return appProxyMapper.selectByUserId(userId);
    }

    @Override
    public List<AppProxy> selectList(AppProxy appProxy) {
        return appProxyMapper.selectList(appProxy);
    }

    @Override
    public int insert(AppProxy appProxy) {
        appProxy.setUpdateTime(DateUtil.now());
        return appProxyMapper.insert(appProxy);
    }

    @Override
    public int update(AppProxy appProxy) {
        appProxy.setUpdateTime(DateUtil.now());
        return appProxyMapper.update(appProxy);
    }

    @Override
    public int deleteByIds(Integer[] ids) {
        return appProxyMapper.deleteByIds(ids);
    }

    @Override
    public int deleteById(Integer id) {
        return appProxyMapper.deleteById(id);
    }

}
