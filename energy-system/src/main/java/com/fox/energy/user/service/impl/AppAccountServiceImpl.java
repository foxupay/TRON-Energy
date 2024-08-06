package com.fox.energy.user.service.impl;

import java.util.List;

import javax.annotation.Resource;

import cn.hutool.crypto.SecureUtil;
import com.fox.energy.common.utils.MessageUtils;
import com.fox.energy.common.utils.security.RsaUtils;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import cn.hutool.core.date.DateUtil;

import com.fox.energy.user.mapper.AppAccountMapper;
import com.fox.energy.user.domain.AppAccount;
import com.fox.energy.user.service.IAppAccountService;

/**
 * 客户端账户体系Service业务层处理
 *
 * @author IEME
 * @date 2024-04-23
 */
@Service
public class AppAccountServiceImpl implements IAppAccountService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private AppAccountMapper appAccountMapper;

    @Override
    public AppAccount selectById(Integer id) {
        return appAccountMapper.selectById(id);
    }

    @Override
    public AppAccount selectByEmail(String email) {
        return appAccountMapper.selectByEmail(email);
    }

    @Override
    public List<AppAccount> selectList(AppAccount appAccount) {
        return appAccountMapper.selectList(appAccount);
    }

    @Override
    public int insert(AppAccount appAccount) {
        appAccount.setCreateTime(DateUtil.now());
        appAccount.setUpdateTime(DateUtil.now());
        return appAccountMapper.insert(appAccount);
    }

    @Override
    public int update(AppAccount appAccount) {
        appAccount.setUpdateTime(DateUtil.now());
        return appAccountMapper.update(appAccount);
    }

    @Override
    public int deleteByIds(Long[] ids) {
        return appAccountMapper.deleteByIds(ids);
    }

    @Override
    public int deleteById(Long id) {
        return appAccountMapper.deleteById(id);
    }

    @Override
    public int resetPwd(Integer id, String password) {
        try {
            password = RsaUtils.decryptByPrivateKey(password.replace(" ", "+"));
        } catch (Exception e) {
            logger.info("密码解密失败 {}", password);
            return -1;
        }
        return appAccountMapper.resetPwd(id, SecureUtil.sha256(id + password));
    }

}
