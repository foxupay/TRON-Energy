package com.fox.energy.tron.server;

import cn.hutool.core.date.DateUtil;
import com.fox.energy.common.core.domain.CommonResult;
import com.fox.energy.common.core.domain.tron.TronAddressCreateModel;
import com.fox.energy.common.core.domain.tron.TronBalance;
import com.fox.energy.tron.util.TronAddressUtil;
import com.fox.energy.tron.util.TronAmountUtil;
import com.fox.energy.tron.util.TronSupport;
import com.fox.energy.tron.domain.TronAddress;
import com.fox.energy.tron.service.ITronAddressService;
import com.fox.energy.tron.util.TronMonitorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class TronAddressServer {

    @Autowired
    private ITronAddressService tronAddressService;
    @Resource
    private TronMonitorUtil tronMonitorUtil;

    public synchronized String getTradeAddress() {
        TronAddress address = tronAddressService.selectNoUsed();
        if (address != null) {
            addressUsed(address.getId());
            return address.getAddress();
        }
        TronAddressCreateModel createModel = TronAddressUtil.create();
        TronAddress tronAddress = new TronAddress();
        tronAddress.setType(1);
        tronAddress.setAddress(createModel.getAddress());
        tronAddress.setPrivateKey(createModel.getPrivateKey());
        tronAddress.setEnable(1);
        tronAddress.setUsed(1);

        CommonResult commonResult = tronMonitorUtil.addAddress(createModel.getAddress());
        if (!commonResult.isSuccess()) {
            return "";
        }
        tronAddressService.insert(tronAddress);
        addressUsed(tronAddress.getId());
        return tronAddress.getAddress();

    }

    public String getUserAddress(Long userId) {
        synchronized (userId.toString().intern()) {
            TronAddress address = tronAddressService.selectByUserId(userId);
            if (address != null) {
                return address.getAddress();
            }
            TronAddressCreateModel createModel = TronAddressUtil.create();
            TronAddress tronAddress = new TronAddress();
            tronAddress.setUserId(userId);
            tronAddress.setType(2);
            tronAddress.setAddress(createModel.getAddress());
            tronAddress.setPrivateKey(createModel.getPrivateKey());
            tronAddress.setEnable(1);
            tronAddress.setUsed(1);

            CommonResult commonResult = tronMonitorUtil.addAddress(createModel.getAddress());
            if (!commonResult.isSuccess()) {
                return "";
            }
            tronAddressService.insert(tronAddress);
            return tronAddress.getAddress();
        }
    }

    public void freezeAddress(String address) {
        TronAddress tronAddress = tronAddressService.selectByAddress(address);
        if (tronAddress == null) {
            return;
        }
        if (tronAddress.getType() != 1) {
            return;
        }
        TronAddress addressUp = new TronAddress();
        addressUp.setId(tronAddress.getId());
        addressUp.setUsed(1);
        addressUp.setUsedTime(DateUtil.now());
        tronAddressService.update(addressUp);
    }

    public void addressUsed(Long id) {
        TronAddress address = new TronAddress();
        address.setId(id);
        address.setUsed(2);
        address.setUsedTime(DateUtil.now());
        tronAddressService.update(address);
    }

    public void syncBalance(String addressStr) {
        TronAddress address = tronAddressService.selectByAddress(addressStr);
        syncBalance(address);
    }

    public void syncBalance(TronAddress address) {
        TronBalance balance = TronSupport.getBalance(address.getAddress());
        TronAddress tronAddress = new TronAddress();
        tronAddress.setId(address.getId());
        tronAddress.setAmtTrx(TronAmountUtil.down(balance.getTrx()));
        tronAddress.setAmtUsdt(TronAmountUtil.down(balance.getUsdt()));
        tronAddressService.update(tronAddress);
    }

}
