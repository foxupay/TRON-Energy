package com.fox.energy.lease.server;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import com.fox.energy.common.constant.QueueConstant;
import com.fox.energy.common.core.domain.CommonResult;
import com.fox.energy.common.core.domain.tron.TronAccountResponse;
import com.fox.energy.common.core.domain.tron.EnergyPriceConfig;
import com.fox.energy.common.utils.LocalUserID;
import com.fox.energy.common.utils.StringUtils;
import com.fox.energy.tron.util.TronSupport;
import com.fox.energy.common.utils.uuid.IdUtils;
import com.fox.energy.framework.mq.JmsProducer;
import com.fox.energy.lease.channel.tool.ChannelContext;
import com.fox.energy.lease.channel.tool.ChannelPayResponse;
import com.fox.energy.lease.channel.tool.ChannelQueryResponse;
import com.fox.energy.lease.domain.AppLeaseTrade;
import com.fox.energy.lease.service.IAppLeaseTradeService;
import com.fox.energy.tron.server.TronAddressServer;
import com.fox.energy.user.domain.AppBalance;
import com.fox.energy.user.domain.AppBalanceAddress;
import com.fox.energy.user.service.IAppBalanceAddressService;
import com.fox.energy.user.service.IAppBalanceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class LeaseTradeServer {

    private Logger log = LoggerFactory.getLogger(LeaseTradeServer.class);

    @Autowired
    private IAppLeaseTradeService appLeaseTradeService;
    @Resource
    private TronAddressServer tronAddressServer;
    @Autowired
    private IAppBalanceService appBalanceService;
    @Autowired
    private IAppBalanceAddressService appBalanceAddressService;
    @Resource
    private JmsProducer jmsProducer;
    @Resource
    private ChannelContext channelContext;

    public CommonResult create(String receiveAddress, Long resourceValue, String leaseDuration, int payWay) {

        //校验地址
        if (!TronSupport.isValid(receiveAddress)) {
            return CommonResult.error("请输入合法的地址");
        }

        TronAccountResponse accountResponse = TronSupport.getAccount(receiveAddress);
        if (accountResponse != null) {
            if (!accountResponse.isActivated()) {
                return CommonResult.error("不支持未激活的地址");
            }
        }
        //获取能量价格
        EnergyPriceConfig priceConfig = TronPriceServer.price();
        if (priceConfig == null) {
            return CommonResult.error("下单失败");
        }
        if (resourceValue > priceConfig.getMax()) {
            return CommonResult.error("能量值不能超过 " + priceConfig.getMax() + " 个");
        }
        if (resourceValue < priceConfig.getMin()) {
            return CommonResult.error("能量值不能低于 " + priceConfig.getMin() + " 个");
        }

        //获取代币汇率
        BigDecimal price = TronPriceServer.rate();
        if (price == null || price.compareTo(BigDecimal.ZERO) == 0) {
            return CommonResult.error("下单失败");
        }

        int timeNumber = Integer.parseInt(leaseDuration.substring(0, leaseDuration.length() - 1));

        BigDecimal priceSun;
        if (leaseDuration.endsWith("h")) {
            priceSun = priceConfig.getH1();
        } else if (leaseDuration.endsWith("d")) {
            if (timeNumber == 1) {
                priceSun = priceConfig.getH24();
            } else if (timeNumber == 3) {
                priceSun = priceConfig.getH72().multiply(BigDecimal.valueOf(timeNumber));
            } else if (timeNumber > 4 && timeNumber <= 30) {
                priceSun = priceConfig.getOther().multiply(BigDecimal.valueOf(timeNumber));
            } else {
                return CommonResult.error("请选择正确的时间");
            }
        } else {
            return CommonResult.error("请选择正确的时间");
        }
        long min = 32000L;
        AppLeaseTrade leaseTrade = new AppLeaseTrade();
        leaseTrade.setUserId(LocalUserID.getStr());
        leaseTrade.setChannel(channelContext.getEnable().getChannel());
        leaseTrade.setOrderNo(IdUtils.orderNo());
        leaseTrade.setReceiveAddress(receiveAddress);
        leaseTrade.setPriceInSun(priceSun);
        leaseTrade.setLeaseType(1);
        leaseTrade.setResourceValue(resourceValue < min ? min : resourceValue);
        BigDecimal payAmount = priceSun.multiply(new BigDecimal(leaseTrade.getResourceValue().toString())).divide(new BigDecimal("1000000"), 2, RoundingMode.UP);
        leaseTrade.setPayTrx(payAmount);
        leaseTrade.setPayUsdt(leaseTrade.getPayTrx().divide(price, 2, RoundingMode.UP));

        if (leaseDuration.endsWith("h")) {
            leaseTrade.setLeaseDurationType(1);
        }
        if (leaseDuration.endsWith("d")) {
            leaseTrade.setLeaseDurationType(2);
        }
        leaseTrade.setLeaseDuration(timeNumber);
        leaseTrade.setPayStatus(1);
        leaseTrade.setLeaseStatus(1);
        int insert = appLeaseTradeService.insert(leaseTrade);
        if (insert <= 0) {
            return CommonResult.error("创建订单失败");
        }
        return pay(leaseTrade, payWay);
    }


    public CommonResult pay(AppLeaseTrade leaseTrade, Integer payWay) {
        //钱包支付
        if (payWay == 1) {
            String address = tronAddressServer.getTradeAddress();
            if (StringUtils.isEmpty(address)) {
                return CommonResult.error("地址支付异常，请使用其他支付方式");
            }
            AppLeaseTrade trade = new AppLeaseTrade();
            trade.setId(leaseTrade.getId());
            trade.setPayAddress(address);
            appLeaseTradeService.update(trade);
            return CommonResult.success()
                    .set("orderNo", leaseTrade.getOrderNo())
                    .set("address", address)
                    .set("trx", leaseTrade.getPayTrx())
                    .set("usdt", leaseTrade.getPayUsdt())
                    .set("expireTime", DateUtil.parse(leaseTrade.getCreateTime()).getTime() + 1000 * 60 * 10)
                    ;
        }
        //用户余额支付
        if (payWay == 2) {
            //判断是否登录
            if (!NumberUtil.isNumber(leaseTrade.getUserId())) {
                AppLeaseTrade trade = new AppLeaseTrade();
                trade.setId(leaseTrade.getId());
                trade.setPayStatus(3);
                appLeaseTradeService.update(trade);
                return CommonResult.error("请注册并登录后使用余额支付");
            }
            CommonResult commonResult = balancePay(Long.parseLong(leaseTrade.getUserId()), leaseTrade.getPayTrx(), leaseTrade.getOrderNo());
            if (commonResult.isSuccess()) {
                AppLeaseTrade trade = new AppLeaseTrade();
                trade.setId(leaseTrade.getId());
                trade.setPayStatus(2);
                trade.setPayWay(1);
                trade.setPayTime(DateUtil.now());
                appLeaseTradeService.update(trade);
                //甩出进行租赁
                jmsProducer.send(QueueConstant.LEASE_DO, leaseTrade.getOrderNo());
                return CommonResult.success("支付成功，正在安排...");
            }
            return commonResult;
        }
        //地址余额支付
        if (payWay == 3) {
            CommonResult commonResult = addressBalancePay(leaseTrade.getReceiveAddress(), leaseTrade.getPayTrx(), leaseTrade.getOrderNo());
            if (commonResult.isSuccess()) {
                AppLeaseTrade trade = new AppLeaseTrade();
                trade.setId(leaseTrade.getId());
                trade.setPayStatus(2);
                trade.setPayWay(2);
                trade.setPayTime(DateUtil.now());
                appLeaseTradeService.update(trade);
                //甩出进行租赁
                jmsProducer.send(QueueConstant.LEASE_DO, leaseTrade.getOrderNo());
                return CommonResult.success("支付成功，正在安排...");
            }
            return commonResult;
        }
        return CommonResult.error("不支持的支付方式");
    }

    public CommonResult balancePay(Long userId, BigDecimal amount, String orderNo) {
        //查询余额
        AppBalance appBalance = appBalanceService.selectByUserId(userId);
        if (appBalance == null || appBalance.getBalance().compareTo(amount) < 0) {
            return CommonResult.error("余额不足");
        }
        appBalanceService.changeBalance(userId, orderNo, amount.negate());
        return CommonResult.success();
    }

    public CommonResult addressBalancePay(String address, BigDecimal amount, String orderNo) {
        //查询余额
        AppBalanceAddress appBalance = appBalanceAddressService.selectByAddress(address);
        if (appBalance == null || appBalance.getBalance().compareTo(amount) < 0) {
            return CommonResult.error("余额不足");
        }
        appBalanceAddressService.changeBalance(address, orderNo, amount.negate());
        return CommonResult.success();
    }


    public ChannelPayResponse lease(AppLeaseTrade trade) {
        log.info("订单租赁 {} - {}", trade.getOrderNo(), trade.getResourceValue());
        return channelContext.pay(trade);
    }

    public ChannelQueryResponse query(AppLeaseTrade trade) {
        log.info("订单查詢 {} - {}", trade.getOrderNo(), trade.getResourceValue());
        return channelContext.query(trade);
    }

    public CommonResult refund(String orderNo) {
        log.info("订单退款 {}", orderNo);
        synchronized (orderNo.intern()) {
            AppLeaseTrade trade = appLeaseTradeService.selectByOrderNo(orderNo);
            if (trade == null) {
                return CommonResult.error("订单不存在");
            }
            if (trade.getPayStatus() != 2 || trade.getLeaseStatus() != 4) {
                return CommonResult.error("状态 不支持退款 PayStatus:" + trade.getPayStatus() + " LeaseStatus:" + trade.getLeaseStatus());
            }
            if (NumberUtil.isNumber(trade.getUserId())) {
                appBalanceService.changeBalance(Long.parseLong(trade.getUserId()), trade.getOrderNo(), trade.getPayTrx());
            } else {
                appBalanceAddressService.changeBalance(trade.getReceiveAddress(), trade.getOrderNo(), trade.getPayTrx());
            }
            AppLeaseTrade tradeUp = new AppLeaseTrade();
            tradeUp.setId(trade.getId());
            tradeUp.setPayStatus(4);
            appLeaseTradeService.update(tradeUp);
            return CommonResult.success();
        }
    }

    public void paySuccess(String orderNo) {
        log.info("订单成功 {}", orderNo);
        synchronized (orderNo.intern()) {
            AppLeaseTrade trade = appLeaseTradeService.selectByOrderNo(orderNo);
            if (trade == null) {
                log.info("订单成功  {}    订单不存在", orderNo);
                return;
            }
            if (trade.getPayStatus() != 1) {
                log.info("订单成功  {}    订单非未支付状态-{}", orderNo, trade.getPayStatus());
                return;
            }
            AppLeaseTrade tradeUp = new AppLeaseTrade();
            tradeUp.setId(trade.getId());
            tradeUp.setPayStatus(2);
            tradeUp.setPayWay(3);
            tradeUp.setPayTime(DateUtil.now());
            appLeaseTradeService.update(tradeUp);
            //解冻地址
            if (StringUtils.isNotEmpty(trade.getPayAddress())) {
                tronAddressServer.freezeAddress(trade.getPayAddress());
            }
            CommonResult.success();
        }
    }
}
