package com.fox.energy.lease.listener;

import cn.hutool.core.date.DateUtil;
import com.fox.energy.common.constant.QueueConstant;
import com.fox.energy.lease.domain.AppLeaseTrade;
import com.fox.energy.lease.server.LeaseTradeServer;
import com.fox.energy.lease.server.TronPriceServer;
import com.fox.energy.lease.service.IAppLeaseTradeService;
import com.fox.energy.tron.domain.TronAddress;
import com.fox.energy.tron.domain.TronTransaction;
import com.fox.energy.tron.server.TronAddressServer;
import com.fox.energy.tron.service.ITronAddressService;
import com.fox.energy.tron.service.ITronTransactionService;
import com.fox.energy.user.service.IAppBalanceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Component
public class TronListener {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ITronTransactionService tronTransactionService;

    @Autowired
    private ITronAddressService tronAddressService;

    @Autowired
    private IAppBalanceService appBalanceService;

    @Autowired
    private IAppLeaseTradeService appLeaseTradeService;

    @Autowired
    private LeaseTradeServer leaseTradeServer;

    @Autowired
    private TronAddressServer tronAddressServer;


    @JmsListener(destination = QueueConstant.TRON_TRANSACTION_HANDLE_DO, containerFactory = "jmsListenerContainerQueue")
    public void task(String hash) {
        logger.info("TRON交易 处理: {}", hash);
        TronTransaction transaction = tronTransactionService.selectByHash(hash);
        if (transaction == null) {
            return;
        }
        if (transaction.getStatus() != 2 || transaction.getHandle() != 2) {
            return;
        }

        TronAddress tronAddress = tronAddressService.selectByAddress(transaction.getToAddress());
        if (tronAddress == null) {
            handle(transaction.getId());
            return;
        }
        if (tronAddress.getType() == 1) {
            tradeSuccess(tronAddress.getAddress(), transaction.getType(), transaction.getAmount());
        }
        if (tronAddress.getType() == 2) {
            BigDecimal amount = transaction.getAmount();
            if (transaction.getType() == 2) {
                BigDecimal price = TronPriceServer.rate();
                if (price == null || price.compareTo(BigDecimal.ZERO) == 0) {
                    return;
                }
                amount = amount.multiply(price).setScale(2, RoundingMode.DOWN);
            }
            if (amount.doubleValue() < 1) {
                logger.info("TRON交易 处理: {} 交易金额低于1TRX", hash);
                handle(transaction.getId());
                return;
            }
            recharge(tronAddress.getUserId(), amount, transaction.getId());
        }
        handle(transaction.getId());
        tronAddressServer.syncBalance(tronAddress.getAddress());
    }

    public void handle(Long id) {
        TronTransaction tronTransaction = new TronTransaction();
        tronTransaction.setId(id);
        tronTransaction.setHandle(3);
        tronTransactionService.update(tronTransaction);
    }

    /**
     * 充值
     *
     * @param userId
     * @param decimal
     */
    public void recharge(Long userId, BigDecimal decimal, Long id) {
        appBalanceService.changeBalance(userId, String.format("R%s-%d", DateUtil.format(DateUtil.date(), "yyyyMMddHHmm"), id), decimal);
    }

    /**
     * 交易成功
     */
    public void tradeSuccess(String address, int type, BigDecimal decimal) {
        //查询订单
        AppLeaseTrade leaseTrade = new AppLeaseTrade();
        leaseTrade.setPayAddress(address);
        leaseTrade.setPayStatus(1);
        List<AppLeaseTrade> trades = appLeaseTradeService.selectList(leaseTrade);
        for (AppLeaseTrade trade : trades) {
            if (type == 1 && trade.getPayTrx().compareTo(decimal) == 0) {
                leaseTradeServer.paySuccess(trade.getOrderNo());
            }
            if (type == 2 && trade.getPayUsdt().compareTo(decimal) == 0) {
                leaseTradeServer.paySuccess(trade.getOrderNo());
            }
        }
    }


}
