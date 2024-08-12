package com.fox.energy.tron.listener;

import cn.hutool.json.JSONUtil;
import com.fox.energy.common.constant.QueueConstant;
import com.fox.energy.common.core.domain.CommonResult;
import com.fox.energy.common.core.domain.tron.TronTransactionModel;
import com.fox.energy.common.utils.StringUtils;
import com.fox.energy.framework.mq.JmsProducer;
import com.fox.energy.tron.domain.TronTransaction;
import com.fox.energy.tron.service.ITronTransactionService;
import com.fox.energy.tron.util.TronAmountUtil;
import com.fox.energy.tron.util.TronSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class TronTransactionListener {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ITronTransactionService tronTransactionService;

    @Autowired
    private JmsProducer jmsProducer;

    @JmsListener(destination = QueueConstant.TRON_TRANSACTION_DO, containerFactory = "jmsListenerContainerQueue")
    public void task(String hash) {
        logger.info("TRON交易 同步: {}", hash);
        TronTransaction transaction = tronTransactionService.selectByHash(hash);
        if (transaction == null) {
            logger.info("TRON交易 同步: {} 不存在的交易", hash);
            return;
        }
        if (transaction.getStatus() != null && (transaction.getStatus() == 3 || transaction.getStatus() == 2)) {
            logger.info("TRON交易 同步: {} 交易已处理", hash);
            return;
        }
        TronTransactionModel model = TronSupport.getTransaction(hash);
        if (model == null) {
            logger.info("TRON交易 同步 {} 接口返回 null", hash);
            return;
        }
        logger.info("TRON交易 同步 {} 接口返回: {}", hash, JSONUtil.toJsonPrettyStr(model));
        transaction.setFromAddress(model.getFrom());
        transaction.setToAddress(model.getTo());
        transaction.setAmount(TronAmountUtil.down(model.getAmount()));
        if ("TRX".equals(model.getSymbol())) {
            transaction.setType(1);
        }
        if ("USDT".equals(model.getSymbol())) {
            transaction.setType(2);
        }
        transaction.setHash(model.getHash());
        CommonResult commonResult = TronSupport.securityAccount(transaction.getFromAddress());
        if (!commonResult.isSuccess()) {
            transaction.setHandle(5);
            transaction.setMessage(commonResult.getMessage());
        } else {
            transaction.setHandle(2);
        }
        if (!model.isContractRet()) {
            transaction.setStatus(3);
            transaction.setMessage(StringUtils.isEmpty(model.getInfo()) ? "交易失败" : model.getInfo());
            tronTransactionService.update(transaction);
        } else if (model.isContractRet() && model.getConfirmations() >= 8) {// && model.isConfirmed()
            transaction.setStatus(2);
            tronTransactionService.update(transaction);
            jmsProducer.send(QueueConstant.TRON_TRANSACTION_HANDLE_DO, transaction.getHash());
        } else {
            transaction.setStatus(1);
            tronTransactionService.update(transaction);
        }

    }


}
