package com.fox.energy.lease.server;

import cn.hutool.core.date.DateUtil;
import com.fox.energy.app.domain.AppApiKey;
import com.fox.energy.app.service.IAppApiKeyService;
import com.fox.energy.common.constant.QueueConstant;
import com.fox.energy.common.core.domain.CommonResult;
import com.fox.energy.common.core.domain.tron.TronAccountResponse;
import com.fox.energy.common.core.domain.tron.EnergyPriceConfig;
import com.fox.energy.common.utils.StringUtils;
import com.fox.energy.tron.util.TronSupport;
import com.fox.energy.common.utils.uuid.IdUtils;
import com.fox.energy.framework.mq.JmsProducer;
import com.fox.energy.framework.web.service.UserLoginService;
import com.fox.energy.lease.domain.ApiLeasePayDto;
import com.fox.energy.lease.domain.AppLeaseTrade;
import com.fox.energy.lease.service.IAppLeaseTradeService;
import com.fox.energy.user.domain.AppAccount;
import com.fox.energy.user.domain.AppBalance;
import com.fox.energy.user.service.IAppAccountService;
import com.fox.energy.user.service.IAppBalanceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class ApiTradeServer {

    private Logger log = LoggerFactory.getLogger(ApiTradeServer.class);

    @Autowired
    private IAppLeaseTradeService appLeaseTradeService;
    @Autowired
    private IAppBalanceService appBalanceService;
    @Resource
    private JmsProducer jmsProducer;
    @Autowired
    private IAppApiKeyService appApiKeyService;
    @Autowired
    private IAppAccountService appAccountService;
    @Autowired
    private UserLoginService userLoginService;

    public CommonResult create(ApiLeasePayDto form) {
        form.setAddress(form.getAddress().replaceAll("\\s+", ""));
        if (form.getAddress().length() != 34) {
            return CommonResult.error("接收资源地址只支持34位长度的地址");
        }
        String uid = "";
        if (StringUtils.isEmpty(form.getApiKey())) {
            try {
                CommonResult result = userLoginService.emailByPwdNoEncrypt(form.getUsername(), form.getPassword());
                if (!result.isSuccess()) {
                    return result;
                }
                uid = result.getData().getStr("uid");
            } catch (Exception e) {
                return CommonResult.error(e.getMessage());
            }
        } else {
            AppApiKey apiKey = appApiKeyService.selectByApiKey(form.getApiKey());
            if (apiKey == null) {
                return CommonResult.error("无效的API密钥");
            }
            AppAccount appAccount = appAccountService.selectById(apiKey.getUserId());
            if (appAccount == null) {
                return CommonResult.error("无效的API密钥");
            }
            if (!appAccount.getEmail().equals(form.getUsername())) {
                return CommonResult.error("无效的API密钥");
            }
            uid = appAccount.getId().toString();
        }

        //校验地址
        if (!TronSupport.isValid(form.getAddress())) {
            return CommonResult.error("请输入合法的地址");
        }

        TronAccountResponse accountResponse = TronSupport.getAccount(form.getAddress());
        if (accountResponse != null) {
            if (!accountResponse.isActivated()) {
                return CommonResult.error("不支持未激活的地址");
            }
        }

        //获取能量价格
        EnergyPriceConfig priceConfig = TronPriceServer.price(Integer.parseInt(uid));
        if (priceConfig == null) {
            return CommonResult.error("下单失败");
        }
        if (form.getAmount() > priceConfig.getMax()) {
            return CommonResult.error("能量值不能超过 " + priceConfig.getMax() + " 个");
        }
        if (form.getAmount() < priceConfig.getMin()) {
            return CommonResult.error("能量值不能低于 " + priceConfig.getMin() + " 个");
        }

        //获取代币汇率
        BigDecimal price = TronPriceServer.rate();
        if (price == null || price.compareTo(BigDecimal.ZERO) == 0) {
            return CommonResult.error("下单失败");
        }

        if (form.getTime().length() < 2) {
            return CommonResult.error("时间格式错误，应为 1d或1h格式");
        }
        int timeNumber = Integer.parseInt(form.getTime().substring(0, form.getTime().length() - 1));

        BigDecimal priceSun;
        if (form.getTime().endsWith("h")) {
            priceSun = priceConfig.getH1();
        } else if (form.getTime().endsWith("d")) {
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

        AppLeaseTrade leaseTrade = new AppLeaseTrade();
        leaseTrade.setUserId(uid);
        leaseTrade.setOrderNo("A" + IdUtils.orderNo());
        leaseTrade.setReceiveAddress(form.getAddress());
        leaseTrade.setPriceInSun(priceSun);
        leaseTrade.setLeaseType(1);
        leaseTrade.setResourceValue(form.getAmount());
        BigDecimal payAmount = priceSun.multiply(new BigDecimal(leaseTrade.getResourceValue().toString())).divide(new BigDecimal("1000000"), 2, RoundingMode.UP);
        leaseTrade.setPayTrx(payAmount);
        leaseTrade.setPayUsdt(leaseTrade.getPayTrx().divide(price, 2, RoundingMode.UP));

        if (form.getTime().endsWith("h")) {
            leaseTrade.setLeaseDurationType(1);
        }
        if (form.getTime().endsWith("d")) {
            leaseTrade.setLeaseDurationType(2);
        }
        leaseTrade.setLeaseDuration(timeNumber);
        leaseTrade.setPayStatus(1);
        leaseTrade.setLeaseStatus(1);
        int insert = appLeaseTradeService.insert(leaseTrade);
        if (insert <= 0) {
            return CommonResult.error("创建订单失败");
        }

        //余额扣费
        CommonResult commonResult = balancePay(Long.parseLong(leaseTrade.getUserId()), leaseTrade.getPayTrx(), leaseTrade.getOrderNo());
        if (!commonResult.isSuccess()) {
            AppLeaseTrade trade = new AppLeaseTrade();
            trade.setId(leaseTrade.getId());
            trade.setPayStatus(3);
            trade.setMessage(commonResult.getMessage());
            appLeaseTradeService.update(trade);
            return commonResult;
        }

        AppLeaseTrade trade = new AppLeaseTrade();
        trade.setId(leaseTrade.getId());
        trade.setPayStatus(2);
        trade.setPayWay(1);
        trade.setPayTime(DateUtil.now());
        appLeaseTradeService.update(trade);
        //甩出进行租赁
        jmsProducer.send(QueueConstant.LEASE_DO, leaseTrade.getOrderNo());
        return CommonResult.success("支付成功，正在安排...").set("orderNo", leaseTrade.getOrderNo());
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


}
