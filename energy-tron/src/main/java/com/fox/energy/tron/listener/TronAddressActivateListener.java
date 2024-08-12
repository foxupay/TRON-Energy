package com.fox.energy.tron.listener;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.fox.energy.common.constant.QueueConstant;
import com.fox.energy.common.core.domain.tron.TronAddressActivateResponse;
import com.fox.energy.tron.domain.TronAddress;
import com.fox.energy.tron.service.ITronAddressService;
import com.fox.energy.tron.util.TronTransferUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * 地址激活
 */
@Component
public class TronAddressActivateListener {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ITronAddressService tronAddressService;


    @JmsListener(destination = QueueConstant.TRON_ADDRESS_ACTIVATE, containerFactory = "jmsListenerContainerQueue")
    public void task(String address) {
        logger.info("激活地址 {}", address);
        TronAddress tronAddress = tronAddressService.selectByAddress(address);
        if (tronAddress == null) {
            return;
        }
        if (tronAddress.getEnable() != 1) {
            return;
        }
        TronAddressActivateResponse activateResponse = TronTransferUtil.activate(tronAddress.getAddress());
        if (!activateResponse.isActivated()) {
            return;
        }
        TronAddress addressUp = new TronAddress();
        addressUp.setId(tronAddress.getId());
        if (StrUtil.isNotBlank(activateResponse.getHash())) {
            addressUp.setEnable(2);
            addressUp.setEnableTime(DateUtil.now());
            addressUp.setEnableHash(activateResponse.getHash());
        } else {
            addressUp.setEnable(3);
        }
        tronAddressService.update(addressUp);
    }

}
