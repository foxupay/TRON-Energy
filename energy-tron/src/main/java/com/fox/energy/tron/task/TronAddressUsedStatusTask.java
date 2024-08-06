package com.fox.energy.tron.task;

import cn.hutool.core.date.DateUtil;
import com.fox.energy.lease.domain.AppLeaseTrade;
import com.fox.energy.lease.service.IAppLeaseTradeService;
import com.fox.energy.tron.domain.TronAddress;
import com.fox.energy.tron.server.TronAddressServer;
import com.fox.energy.tron.service.ITronAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("tronAddressUsedStatusTask")
public class TronAddressUsedStatusTask {

    @Autowired
    private IAppLeaseTradeService appLeaseTradeService;
    @Autowired
    private TronAddressServer tronAddressServer;
    @Autowired
    private ITronAddressService tronAddressService;

    public void doTask() {
        TronAddress addressQuery = new TronAddress();
        addressQuery.setUsed(2);
        List<TronAddress> list = tronAddressService.selectList(addressQuery);
        for (TronAddress address : list) {
            //超过30分钟，失败
            if (System.currentTimeMillis() - DateUtil.parse(address.getUsedTime()).getTime() < 1000 * 60 * 10) {
                continue;
            }
            tronAddressServer.freezeAddress(address.getAddress());
            //查找订单
            AppLeaseTrade tradeQuery = new AppLeaseTrade();
            tradeQuery.setPayAddress(address.getAddress());
            tradeQuery.setPayStatus(1);
            List<AppLeaseTrade> tradeList = appLeaseTradeService.selectList(tradeQuery);
            for (AppLeaseTrade trade : tradeList) {
                AppLeaseTrade leaseTrade = new AppLeaseTrade();
                leaseTrade.setId(trade.getId());
                leaseTrade.setPayStatus(3);
                appLeaseTradeService.update(leaseTrade);
            }

        }
    }

}
