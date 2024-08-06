package com.fox.energy.user.service.impl;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;

import com.fox.energy.common.exception.ServiceException;
import com.fox.energy.user.domain.AppBalance;
import com.fox.energy.user.domain.AppBalanceLog;
import com.fox.energy.user.service.IAppBalanceLogService;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import cn.hutool.core.date.DateUtil;

import com.fox.energy.user.mapper.AppBalanceAddressMapper;
import com.fox.energy.user.domain.AppBalanceAddress;
import com.fox.energy.user.service.IAppBalanceAddressService;

/**
 * 地址余额Service业务层处理
 *
 * @author IEME
 * @date 2024-05-14
 */
@Service
public class AppBalanceAddressServiceImpl implements IAppBalanceAddressService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private AppBalanceAddressMapper appBalanceAddressMapper;

    @Resource
    private IAppBalanceLogService appBalanceLogService;

    @Override
    public AppBalanceAddress selectById(Long id) {
        return appBalanceAddressMapper.selectById(id);
    }

    @Override
    public AppBalanceAddress selectByAddress(String address) {
        synchronized (address.intern()) {
            AppBalanceAddress appBalance = appBalanceAddressMapper.selectByAddress(address);
            if (appBalance == null) {
                appBalance = new AppBalanceAddress();
                appBalance.setAddress(address);
                appBalance.setBalance(BigDecimal.ZERO);
                insert(appBalance);
            }
            return appBalance;
        }
    }

    @Override
    public AppBalanceAddress getByAddress(String address) {
        return appBalanceAddressMapper.selectByAddress(address);
    }

    @Override
    public List<AppBalanceAddress> selectList(AppBalanceAddress appBalanceAddress) {
        return appBalanceAddressMapper.selectList(appBalanceAddress);
    }

    @Override
    public int insert(AppBalanceAddress appBalanceAddress) {
        appBalanceAddress.setCreateTime(DateUtil.now());
        appBalanceAddress.setUpdateTime(DateUtil.now());
        int insert = appBalanceAddressMapper.insert(appBalanceAddress);
        AppBalanceLog log = new AppBalanceLog();
        log.setUserId(appBalanceAddress.getAddress());
        log.setOrderNo("CREATE_" + System.currentTimeMillis());
        log.setBeforeBalance(BigDecimal.ZERO);
        log.setAmount(appBalanceAddress.getBalance());
        log.setAfterBalance(appBalanceAddress.getBalance());
        appBalanceLogService.insert(log);
        return insert;
    }

    @Override
    public int update(AppBalanceAddress appBalanceAddress) {
        appBalanceAddress.setUpdateTime(DateUtil.now());
        return appBalanceAddressMapper.update(appBalanceAddress);
    }

    @Override
    public int change(String address, BigDecimal amount) {
        return appBalanceAddressMapper.change(address, amount);
    }

    @Override
    public int deleteByIds(Long[] ids) {
        return appBalanceAddressMapper.deleteByIds(ids);
    }

    @Override
    public int deleteById(Long id) {
        return appBalanceAddressMapper.deleteById(id);
    }

    @Override
    public void changeBalance(String address, String orderNo, BigDecimal amount) {
        synchronized (address.intern()) {
            AppBalanceAddress before = selectByAddress(address);
            AppBalanceLog log = new AppBalanceLog();
            log.setUserId(address);
            log.setOrderNo(orderNo);
            log.setAmount(amount);
            log.setBeforeBalance(before.getBalance());
            log.setAmount(amount);
            int change = change(address, amount);
            if (change == 0) {
                logger.error("用户余额变更失败，address:{}", address);
                throw new ServiceException("用户余额变更失败");
            }
            AppBalanceAddress after = selectByAddress(address);
            log.setAfterBalance(after.getBalance());
            appBalanceLogService.insert(log);
        }
    }

}
