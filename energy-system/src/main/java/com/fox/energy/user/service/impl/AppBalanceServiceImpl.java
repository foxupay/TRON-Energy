package com.fox.energy.user.service.impl;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;

import com.fox.energy.common.exception.ServiceException;
import com.fox.energy.user.domain.AppBalanceLog;
import com.fox.energy.user.service.IAppBalanceLogService;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import cn.hutool.core.date.DateUtil;

import com.fox.energy.user.mapper.AppBalanceMapper;
import com.fox.energy.user.domain.AppBalance;
import com.fox.energy.user.service.IAppBalanceService;

/**
 * 用户余额Service业务层处理
 *
 * @author IEME
 * @date 2024-04-30
 */
@Service
public class AppBalanceServiceImpl implements IAppBalanceService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private AppBalanceMapper appBalanceMapper;

    @Resource
    private IAppBalanceLogService appBalanceLogService;

    @Override
    public AppBalance selectById(Long id) {
        return appBalanceMapper.selectById(id);
    }

    @Override
    public AppBalance selectByUserId(Long userId) {
        synchronized (userId.toString().intern()) {
            AppBalance appBalance = appBalanceMapper.selectByUserId(userId);
            if (appBalance == null) {
                appBalance = new AppBalance();
                appBalance.setUserId(userId);
                appBalance.setBalance(BigDecimal.ZERO);
                insert(appBalance);
            }
            return appBalance;
        }
    }

    @Override
    public List<AppBalance> selectList(AppBalance appBalance) {
        return appBalanceMapper.selectList(appBalance);
    }

    @Override
    public int insert(AppBalance appBalance) {
        appBalance.setCreateTime(DateUtil.now());
        appBalance.setUpdateTime(DateUtil.now());

        int insert = appBalanceMapper.insert(appBalance);

        AppBalanceLog log = new AppBalanceLog();
        log.setUserId(appBalance.getUserId().toString());
        log.setOrderNo("CREATE_" + System.currentTimeMillis());
        log.setBeforeBalance(BigDecimal.ZERO);
        log.setAmount(appBalance.getBalance());
        log.setAfterBalance(appBalance.getBalance());
        appBalanceLogService.insert(log);

        return insert;
    }

    @Override
    public int update(AppBalance appBalance) {
        appBalance.setUpdateTime(DateUtil.now());
        return appBalanceMapper.update(appBalance);
    }

    @Override
    public int change(Long userId, BigDecimal amount) {
        return appBalanceMapper.change(userId, amount);
    }

    @Override
    public int deleteByIds(Long[] ids) {
        return appBalanceMapper.deleteByIds(ids);
    }

    @Override
    public int deleteById(Long id) {
        return appBalanceMapper.deleteById(id);
    }

    @Override
    public void changeBalance(Long userId, String orderNo, BigDecimal amount) {
        synchronized (userId.toString().intern()) {
            AppBalance before = selectByUserId(userId);
            AppBalanceLog log = new AppBalanceLog();
            log.setUserId(userId.toString());
            log.setOrderNo(orderNo);
            log.setAmount(amount);
            log.setBeforeBalance(before.getBalance());
            log.setAmount(amount);
            int change = change(userId, amount);
            if (change == 0) {
                logger.error("用户余额变更失败，userId:{}", userId);
                throw new ServiceException("用户余额变更失败");
            }
            AppBalance after = selectByUserId(userId);
            log.setAfterBalance(after.getBalance());
            appBalanceLogService.insert(log);
        }
    }

}
