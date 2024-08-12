package com.fox.energy.tron.listener;

import cn.hutool.json.JSONUtil;
import com.fox.energy.common.constant.QueueConstant;
import com.fox.energy.common.core.domain.tron.TronMonitorModel;
import com.fox.energy.framework.mq.JmsProducer;
import com.fox.energy.tron.domain.TronTransaction;
import com.fox.energy.tron.service.ITronTransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class TronCallListener {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ITronTransactionService tronTransactionService;

    @Autowired
    private JmsProducer jmsProducer;


    @JmsListener(destination = QueueConstant.TRON_CALL_DO, containerFactory = "jmsListenerContainerQueue")
    public void task(String json) {
        logger.info("TRON交易监听: {}", json);
        TronMonitorModel model = JSONUtil.toBean(json, TronMonitorModel.class);
        TronTransaction transaction = tronTransactionService.selectByHash(model.getTxID());
        if (transaction != null) {
            return;
        }

        transaction = new TronTransaction();
        transaction.setHash(model.getTxID());
        transaction.setHandle(1);
        tronTransactionService.insert(transaction);
        jmsProducer.send(QueueConstant.TRON_TRANSACTION_DO, model.getTxID());
    }

}
