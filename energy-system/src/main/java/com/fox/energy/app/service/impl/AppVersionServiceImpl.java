package com.fox.energy.app.service.impl;

import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import cn.hutool.core.date.DateUtil;

import com.fox.energy.app.mapper.AppVersionMapper;
import com.fox.energy.app.domain.AppVersion;
import com.fox.energy.app.service.IAppVersionService;

/**
 * APP版本Service业务层处理
 *
 * @author IEME
 * @date 2024-05-27
 */
@Service
public class AppVersionServiceImpl implements IAppVersionService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private AppVersionMapper appVersionMapper;

    @Override
    public AppVersion selectById(Integer id) {
        return appVersionMapper.selectById(id);
    }

    @Override
    public List<AppVersion> selectList(AppVersion appVersion) {
        return appVersionMapper.selectList(appVersion);
    }

    @Override
    public int insert(AppVersion appVersion) {
            return appVersionMapper.insert(appVersion);
    }

    @Override
    public int update(AppVersion appVersion) {
        return appVersionMapper.update(appVersion);
    }

    @Override
    public int deleteByIds(Integer[] ids) {
        return appVersionMapper.deleteByIds(ids);
    }

    @Override
    public int deleteById(Integer id) {
        return appVersionMapper.deleteById(id);
    }

}
