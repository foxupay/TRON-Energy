package com.fox.energy.app.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import cn.hutool.core.date.DateUtil;

import com.fox.energy.app.mapper.AppApiKeyMapper;
import com.fox.energy.app.domain.AppApiKey;
import com.fox.energy.app.service.IAppApiKeyService;

/**
 * 接口秘钥Service业务层处理
 *
 * @author IEME
 * @date 2024-07-24
 */
@Service
public class AppApiKeyServiceImpl implements IAppApiKeyService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private AppApiKeyMapper appApiKeyMapper;

    @Override
    public AppApiKey selectById(Integer id) {
        return appApiKeyMapper.selectById(id);
    }

    @Override
    public AppApiKey selectByApiKey(String apiKey) {
        return appApiKeyMapper.selectByApiKey(apiKey);
    }

    @Override
    public List<AppApiKey> selectList(AppApiKey appApiKey) {
        return appApiKeyMapper.selectList(appApiKey);
    }

    @Override
    public int insert(AppApiKey appApiKey) {
        appApiKey.setUpdateTime(DateUtil.now());
        return appApiKeyMapper.insert(appApiKey);
    }

    @Override
    public int update(AppApiKey appApiKey) {
        appApiKey.setUpdateTime(DateUtil.now());
        return appApiKeyMapper.update(appApiKey);
    }

    @Override
    public int deleteByIds(Integer[] ids) {
        return appApiKeyMapper.deleteByIds(ids);
    }

    @Override
    public int deleteById(Integer id) {
        return appApiKeyMapper.deleteById(id);
    }

}
