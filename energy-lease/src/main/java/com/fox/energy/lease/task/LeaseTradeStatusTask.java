package com.fox.energy.lease.task;

import cn.hutool.core.date.DateUtil;
import com.fox.energy.common.utils.StringUtils;
import com.fox.energy.lease.domain.AppLeaseTrade;
import com.fox.energy.lease.service.IAppLeaseTradeService;
import com.fox.energy.tron.server.TronAddressServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("leaseTradeStatusTask")
public class LeaseTradeStatusTask {

    @Autowired
    private IAppLeaseTradeService appLeaseTradeService;
    @Autowired
    private TronAddressServer tronAddressServer;

    public void doTask() {
        AppLeaseTrade leaseTradeQuery = new AppLeaseTrade();
        leaseTradeQuery.setPayStatus(1);
        List<AppLeaseTrade> trades = appLeaseTradeService.selectList(leaseTradeQuery);
        for (AppLeaseTrade leaseTrade : trades) {
            //超过10分钟，失败
            if (System.currentTimeMillis() - DateUtil.parse(leaseTrade.getCreateTime()).getTime() < 1000 * 60 * 10) {
                continue;
            }
            AppLeaseTrade trade = new AppLeaseTrade();
            trade.setId(leaseTrade.getId());
            trade.setPayStatus(3);
            appLeaseTradeService.update(trade);
            if (StringUtils.isNotEmpty(leaseTrade.getPayAddress())) {
                tronAddressServer.freezeAddress(leaseTrade.getPayAddress());
            }
        }
    }

}
