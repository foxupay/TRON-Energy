package com.fox.energy.tron.task;

import com.fox.energy.common.constant.QueueConstant;
import com.fox.energy.framework.mq.JmsProducer;
import com.fox.energy.tron.domain.TronTransaction;
import com.fox.energy.tron.service.ITronTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("tronTransactionStatusTask")
public class TronTransactionStatusTask {

    @Autowired
    private ITronTransactionService tronTransactionService;

    @Autowired
    private JmsProducer jmsProducer;

    public void doTask() {
        TronTransaction transQuery = new TronTransaction();
        transQuery.setHandle(1);
        List<TronTransaction> list = tronTransactionService.selectList(transQuery);
        for (TronTransaction transaction : list) {
            jmsProducer.send(QueueConstant.TRON_TRANSACTION_DO, transaction.getHash());
        }
    }


    public void waitPay() {
        TronTransaction transQuery = new TronTransaction();
        transQuery.setStatus(1);
        List<TronTransaction> list = tronTransactionService.selectList(transQuery);
        for (TronTransaction transaction : list) {
            jmsProducer.send(QueueConstant.TRON_TRANSACTION_DO, transaction.getHash());
        }
    }


    public void waitHandle() {
        TronTransaction transQuery = new TronTransaction();
        transQuery.setStatus(2);
        transQuery.setHandle(2);
        List<TronTransaction> list = tronTransactionService.selectList(transQuery);
        for (TronTransaction transaction : list) {
            jmsProducer.send(QueueConstant.TRON_TRANSACTION_HANDLE_DO, transaction.getHash());
        }
    }


}
