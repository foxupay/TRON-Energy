package com.fox.energy.user.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import cn.hutool.core.date.DateUtil;

import com.fox.energy.user.mapper.AppBalanceLogMapper;
import com.fox.energy.user.domain.AppBalanceLog;
import com.fox.energy.user.service.IAppBalanceLogService;

/**
 * 用户余额记录Service业务层处理
 *
 * @author IEME
 * @date 2024-05-13
 */
@Service
public class AppBalanceLogServiceImpl implements IAppBalanceLogService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private AppBalanceLogMapper appBalanceLogMapper;

    @Override
    public AppBalanceLog selectById(Long id) {
        return appBalanceLogMapper.selectById(id);
    }

    @Override
    public List<AppBalanceLog> selectList(AppBalanceLog appBalanceLog) {
        return appBalanceLogMapper.selectList(appBalanceLog);
    }

    @Override
    public int insert(AppBalanceLog appBalanceLog) {
        appBalanceLog.setCreateAt(DateUtil.now());
        return appBalanceLogMapper.insert(appBalanceLog);
    }

    @Override
    public int update(AppBalanceLog appBalanceLog) {
        return appBalanceLogMapper.update(appBalanceLog);
    }

    @Override
    public int deleteByIds(Long[] ids) {
        return appBalanceLogMapper.deleteByIds(ids);
    }

    @Override
    public int deleteById(Long id) {
        return appBalanceLogMapper.deleteById(id);
    }

}
