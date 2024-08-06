package com.fox.energy.tron.controller;

import com.fox.energy.common.annotation.Log;
import com.fox.energy.common.constant.QueueConstant;
import com.fox.energy.common.core.controller.BaseController;
import com.fox.energy.common.core.domain.AjaxResult;
import com.fox.energy.common.core.page.TableDataInfo;
import com.fox.energy.common.enums.BusinessType;
import com.fox.energy.common.utils.poi.ExcelUtil;
import com.fox.energy.framework.mq.JmsProducer;
import com.fox.energy.tron.domain.TronAddress;
import com.fox.energy.tron.server.TronAddressServer;
import com.fox.energy.tron.service.ITronAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * TRON地址Controller
 *
 * @author IEME
 * @date 2024-05-13
 */
@RestController
@RequestMapping("/tron/address")
public class TronAddressController extends BaseController {
    @Autowired
    private ITronAddressService tronAddressService;
    @Autowired
    private JmsProducer jmsProducer;
    @Resource
    private TronAddressServer tronAddressServer;

    /**
     * 查询TRON地址列表
     */
    @PreAuthorize("@ss.hasPermi('tron:address:list')")
    @GetMapping("/list")
    public TableDataInfo list(TronAddress tronAddress) {
        startPage();
        List<TronAddress> list = tronAddressService.selectList(tronAddress);
        return getDataTable(list);
    }

    /**
     * 导出TRON地址列表
     */
    @PreAuthorize("@ss.hasPermi('tron:address:export')")
    @Log(title = "TRON地址", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TronAddress tronAddress) {
        List<TronAddress> list = tronAddressService.selectList(tronAddress);
        ExcelUtil<TronAddress> util = new ExcelUtil<TronAddress>(TronAddress.class);
        util.exportExcel(response, list, "TRON地址数据");
    }

    /**
     * 获取TRON地址详细信息
     */
    @PreAuthorize("@ss.hasPermi('tron:address:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(tronAddressService.selectById(id));
    }

    /**
     * 新增TRON地址
     */
    @PreAuthorize("@ss.hasPermi('tron:address:add')")
    @Log(title = "TRON地址", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TronAddress tronAddress) {
        return toAjax(tronAddressService.insert(tronAddress));
    }

    /**
     * 修改TRON地址
     */
    @PreAuthorize("@ss.hasPermi('tron:address:edit')")
    @Log(title = "TRON地址", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TronAddress tronAddress) {
        return toAjax(tronAddressService.update(tronAddress));
    }

    /**
     * 删除TRON地址
     */
    @PreAuthorize("@ss.hasPermi('tron:address:remove')")
    @Log(title = "TRON地址", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(tronAddressService.deleteByIds(ids));
    }


    /**
     * 地址激活
     */
    @Log(title = "TRON地址-激活", businessType = BusinessType.OTHER)
    @PostMapping("/activate")
    public AjaxResult activate(@RequestBody TronAddress address) {
        jmsProducer.send(QueueConstant.TRON_ADDRESS_ACTIVATE, address.getAddress());
        return success();
    }

    /**
     * 同步余额
     */
    @Log(title = "TRON地址-同步余额", businessType = BusinessType.OTHER)
    @PostMapping("/syncBalance")
    public AjaxResult syncBalance(@RequestBody TronAddress address) {
        tronAddressServer.syncBalance(address.getAddress());
        return success();
    }


}
