package com.fox.energy.tron.task;

import com.fox.energy.common.core.domain.tron.TronAccountResponse;
import com.fox.energy.tron.util.TronSupport;
import com.fox.energy.tron.domain.TronAddress;
import com.fox.energy.tron.service.ITronAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 定时更新地址的激活状态
 */
@Component("tronAddressActivateStatusTask")
public class TronAddressActivateStatusTask {

    @Autowired
    private ITronAddressService tronAddressService;

    public void doTask() {
        TronAddress addressQuery = new TronAddress();
        addressQuery.setEnable(2);
        List<TronAddress> list = tronAddressService.selectList(addressQuery);
        for (TronAddress address : list) {
            TronAccountResponse response = TronSupport.getAccount(address.getAddress());
            if (response == null) {
                return;
            }
            if (!response.isActivated()) {
                return;
            }
            TronAddress addressUp = new TronAddress();
            addressUp.setId(address.getId());
            addressUp.setEnable(3);
            tronAddressService.update(addressUp);
        }
    }

}
