package com.fox.energy.tron.task;

import com.fox.energy.tron.domain.TronAddress;
import com.fox.energy.tron.server.TronAddressServer;
import com.fox.energy.tron.service.ITronAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("tronAddressBalanceUpdateTask")
public class TronAddressBalanceUpdateTask {

    @Autowired
    private ITronAddressService tronAddressService;

    @Autowired
    private TronAddressServer tronAddressServer;

    public void doTask() {
        TronAddress addressQuery = new TronAddress();
        List<TronAddress> list = tronAddressService.selectList(addressQuery);
        for (TronAddress address : list) {
            try {
                tronAddressServer.syncBalance(address);
            } catch (Exception ignore) {

            }
        }
    }

}
