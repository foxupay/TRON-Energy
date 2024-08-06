package com.fox.energy.conf.service.impl;

import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import cn.hutool.core.date.DateUtil;

import com.fox.energy.conf.mapper.AppConfMailMapper;
import com.fox.energy.conf.domain.AppConfMail;
import com.fox.energy.conf.service.IAppConfMailService;

/**
 * 邮件配置Service业务层处理
 *
 * @author IEME
 * @date 2024-07-22
 */
@Service
public class AppConfMailServiceImpl implements IAppConfMailService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private AppConfMailMapper appConfMailMapper;

    @Override
    public AppConfMail selectById(Long id) {
        return appConfMailMapper.selectById(id);
    }

    @Override
    public List<AppConfMail> selectList(AppConfMail appConfMail) {
        return appConfMailMapper.selectList(appConfMail);
    }

    @Override
    public int insert(AppConfMail appConfMail) {
                appConfMail.setUpdateTime(DateUtil.now());
            return appConfMailMapper.insert(appConfMail);
    }

    @Override
    public int update(AppConfMail appConfMail) {
                appConfMail.setUpdateTime(DateUtil.now());
        return appConfMailMapper.update(appConfMail);
    }

    @Override
    public int deleteByIds(Long[] ids) {
        return appConfMailMapper.deleteByIds(ids);
    }

    @Override
    public int deleteById(Long id) {
        return appConfMailMapper.deleteById(id);
    }

}
