package com.fox.energy.web.api;

import com.fox.energy.common.annotation.APILog;
import com.fox.energy.common.annotation.AppLoginNotRequired;
import com.fox.energy.common.core.controller.BaseController;
import com.fox.energy.common.core.domain.AjaxResult;
import com.fox.energy.common.utils.StringUtils;
import com.fox.energy.lease.domain.ApiLeasePayDto;
import com.fox.energy.lease.domain.AppLeaseTrade;
import com.fox.energy.lease.server.ApiTradeServer;
import com.fox.energy.lease.server.TronPriceServer;
import com.fox.energy.lease.service.IAppLeaseTradeService;
import com.fox.energy.user.domain.AppAccount;
import com.fox.energy.user.domain.AppBalance;
import com.fox.energy.user.service.IAppAccountService;
import com.fox.energy.user.service.IAppBalanceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"4-API接口"})
@RestController
@RequestMapping({"/external"})
public class ApiLeaseController extends BaseController {

    @Autowired
    private ApiTradeServer apiTradeServer;

    @Autowired
    private IAppLeaseTradeService appLeaseTradeService;

    @Autowired
    private IAppBalanceService appBalanceService;
    @Autowired
    private IAppAccountService appAccountService;

    @ApiOperation("支付订单")
    @APILog(title = "支付订单")
    @PostMapping({"/pay"})
    public AjaxResult create(@Validated @RequestBody ApiLeasePayDto form) {
        return result(apiTradeServer.create(form));
    }

    @ApiOperation("余额")
    @APILog(title = "余额")
    @GetMapping({"/balance"})
    public AjaxResult balance(@RequestParam String username) {
        AppAccount appAccount = appAccountService.selectByEmail(username);
        if (appAccount == null) {
            return success().set("balance", 0);
        }
        AppBalance appBalance = appBalanceService.selectByUserId(appAccount.getId().longValue());
        if (appBalance == null) {
            return success().set("balance", 0);
        }
        return success().set("balance", appBalance.getBalance().toPlainString());
    }

    @ApiOperation("查询支付状态")
    @APILog(title = "查询支付状态")
    @GetMapping("/query")
    public AjaxResult query(@RequestParam String orderNo) {
        AppLeaseTrade leaseTrade = appLeaseTradeService.selectByOrderNo(orderNo);
        if (leaseTrade == null) {
            return AjaxResult.error("订单不存在");
        }
        return success()
                .set("orderNo", leaseTrade.getOrderNo())
                .set("address", leaseTrade.getReceiveAddress())
                .set("amount", leaseTrade.getResourceValue())
                .set("time", leaseTrade.getLeaseDuration() + (leaseTrade.getLeaseDurationType() == 1 ? "h" : "d"))
                .set("price", leaseTrade.getPriceInSun())
                .set("trx", leaseTrade.getPayTrx())
                .set("hash", leaseTrade.getFrozenTxId())
                .set("status", leaseTrade.getLeaseStatus())
                ;
    }

    @ApiOperation("交易配置信息")
    @APILog(title = "交易配置信息")
    @GetMapping("/config")
    public AjaxResult config(@RequestParam(required = false, defaultValue = "") String username) {
        if (StringUtils.isEmpty(username)) {
            return success(TronPriceServer.price());
        }
        AppAccount appAccount = appAccountService.selectByEmail(username);
        if (appAccount == null) {
            return success(TronPriceServer.price());
        }
        return success(TronPriceServer.price(appAccount.getId()));
    }

}
