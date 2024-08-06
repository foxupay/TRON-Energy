package com.fox.energy.web.app.lease;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.NumberUtil;
import com.fox.energy.common.annotation.APILog;
import com.fox.energy.common.annotation.AppLoginNotRequired;
import com.fox.energy.common.core.controller.BaseController;
import com.fox.energy.common.core.domain.AjaxResult;
import com.fox.energy.common.core.page.TableDataInfo;
import com.fox.energy.common.utils.LocalUserID;
import com.fox.energy.lease.domain.AppLeaseTrade;
import com.fox.energy.lease.domain.vo.AppLeaseTradeVo;
import com.fox.energy.lease.server.LeaseTradeServer;
import com.fox.energy.lease.server.TronPriceServer;
import com.fox.energy.lease.service.IAppLeaseTradeService;
import com.fox.energy.tron.domain.TronTransaction;
import com.fox.energy.tron.service.ITronTransactionService;
import com.fox.energy.web.domain.AppLeaseCreateForm;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Api(tags = {"3-APP-租赁"})
@RestController
@RequestMapping({"/app/lease"})
public class AppLeaseController extends BaseController {

    @Autowired
    private LeaseTradeServer leaseTradeServer;

    @Autowired
    private IAppLeaseTradeService appLeaseTradeService;

    @Autowired
    private ITronTransactionService tronTransactionService;

    @ApiOperation("支付订单")
    @APILog(title = "支付订单")
    @PostMapping({"/create"})
    @AppLoginNotRequired
    public AjaxResult create(@Validated @RequestBody AppLeaseCreateForm form) {
        return result(leaseTradeServer.create(form.getReceiveAddress().replaceAll(" ", ""), form.getResourceValue(), form.getLeaseDuration(), form.getPayWay()));
    }

    @ApiOperation("查询支付状态")
    @APILog(title = "查询支付状态")
    @GetMapping("/query")
    public AjaxResult query(@RequestParam String orderNo) {
        AppLeaseTrade leaseTrade = appLeaseTradeService.selectByOrderNo(orderNo);
        if (leaseTrade == null) {
            return AjaxResult.error("订单不存在");
        }
        return success().set("status", leaseTrade.getPayStatus());
    }

    @ApiOperation("查询支付状态")
    @APILog(title = "查询支付状态")
    @GetMapping("/hash")
    public AjaxResult hash(@RequestParam String hash) {
        TronTransaction tronTransaction = tronTransactionService.selectByHash(hash);
        if (tronTransaction == null) {
            //订单未支付
            return success().set("status", 1);
        }
        if (tronTransaction.getHandle() == 1) {
            //订单确认中
            return success().set("status", 2);
        }
        if (tronTransaction.getStatus() == null) {
            return success().set("status", 2);
        }
        if (tronTransaction.getStatus() == 2) {
            //支付成功
            return success().set("status", 3);
        }
        if (tronTransaction.getStatus() == 3) {
            //支付失败
            return success().set("status", 4);
        }
        return success().set("status", 2);
    }

    @ApiOperation("查询订单列表")
    @APILog(title = "查询订单列表")
    @AppLoginNotRequired
    @GetMapping("/list")
    public TableDataInfo list() {
        startPage();
        PageHelper.orderBy("id desc");
        AppLeaseTrade appLeaseTrade = new AppLeaseTrade();
        appLeaseTrade.setUserId(LocalUserID.getStr());
        appLeaseTrade.setPayStatus(2);
        List<AppLeaseTrade> list = appLeaseTradeService.selectList(appLeaseTrade);
        return getDataTable(list);
    }

    @ApiOperation("最新订单")
    @APILog(title = "最新订单")
    @GetMapping("/new")
    public AjaxResult newOrder() {
        PageHelper.startPage(1, 10);
        PageHelper.orderBy("id desc");
        AppLeaseTrade appLeaseTrade = new AppLeaseTrade();
        appLeaseTrade.setPayStatus(2);
        List<AppLeaseTrade> list = appLeaseTradeService.selectList(appLeaseTrade);
        List<AppLeaseTradeVo> vos = new ArrayList<>();
        for (AppLeaseTrade leaseTrade : list) {
            AppLeaseTradeVo appLeaseTradeVo = BeanUtil.copyProperties(leaseTrade, AppLeaseTradeVo.class);
            appLeaseTradeVo.setPayAmount(leaseTrade.getPayTrx());
            vos.add(appLeaseTradeVo);
        }
        return success(vos);
    }


    @ApiOperation("交易配置信息")
    @APILog(title = "交易配置信息")
    @GetMapping("/config")
    @AppLoginNotRequired
    public AjaxResult config() {
        return success(TronPriceServer.price());
    }

    @ApiOperation("USDT/TRX汇率")
    @APILog(title = "USDT/TRX汇率")
    @GetMapping("/rate")
    public AjaxResult rate() {
        return success().set("rate", TronPriceServer.rate());
    }
}
