package com.fox.energy.tron.controller;

import com.fox.energy.common.annotation.Log;
import com.fox.energy.common.constant.QueueConstant;
import com.fox.energy.common.core.controller.BaseController;
import com.fox.energy.common.core.domain.AjaxResult;
import com.fox.energy.common.core.page.TableDataInfo;
import com.fox.energy.common.enums.BusinessType;
import com.fox.energy.common.utils.poi.ExcelUtil;
import com.fox.energy.framework.mq.JmsProducer;
import com.fox.energy.tron.domain.TronTransaction;
import com.fox.energy.tron.service.ITronTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * TRON交易Controller
 *
 * @author IEME
 * @date 2024-05-11
 */
@RestController
@RequestMapping("/tron/transaction")
public class TronTransactionController extends BaseController {
    @Autowired
    private ITronTransactionService tronTransactionService;

    @Autowired
    private JmsProducer jmsProducer;

    /**
     * 查询TRON交易列表
     */
    @PreAuthorize("@ss.hasPermi('tron:transaction:list')")
    @GetMapping("/list")
    public TableDataInfo list(TronTransaction tronTransaction) {
        startPage();
        List<TronTransaction> list = tronTransactionService.selectList(tronTransaction);
        return getDataTable(list);
    }

    /**
     * 导出TRON交易列表
     */
    @PreAuthorize("@ss.hasPermi('tron:transaction:export')")
    @Log(title = "TRON交易", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TronTransaction tronTransaction) {
        List<TronTransaction> list = tronTransactionService.selectList(tronTransaction);
        ExcelUtil<TronTransaction> util = new ExcelUtil<TronTransaction>(TronTransaction.class);
        util.exportExcel(response, list, "TRON交易数据");
    }

    /**
     * 获取TRON交易详细信息
     */
    @PreAuthorize("@ss.hasPermi('tron:transaction:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(tronTransactionService.selectById(id));
    }

    /**
     * 修改TRON交易
     */
    @PreAuthorize("@ss.hasPermi('tron:transaction:edit')")
    @Log(title = "TRON交易", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TronTransaction transaction) {
        if (transaction.getHandle() == 5) {
            tronTransactionService.update(transaction);
        }
        if (transaction.getHandle() == 2) {
            tronTransactionService.update(transaction);
            TronTransaction tronTransaction = tronTransactionService.selectById(transaction.getId());
            if (tronTransaction == null) {
                return AjaxResult.error("TRON交易不存在");
            }
            if (tronTransaction.getStatus() != 2) {
                return AjaxResult.error("TRON交易未成功");
            }
            if (tronTransaction.getHandle() == 1) {
                return AjaxResult.error("TRON交易未同步");
            }
            if (tronTransaction.getHandle() == 3) {
                return AjaxResult.error("TRON交易已处理");
            }
            jmsProducer.send(QueueConstant.TRON_TRANSACTION_HANDLE_DO, tronTransaction.getHash());
        }
        return success();
    }

    /**
     * TRON交易处理
     */
    @PreAuthorize("@ss.hasPermi('tron:transaction:remove')")
    @Log(title = "TRON交易处理", businessType = BusinessType.UPDATE)
    @DeleteMapping("/handle/{id}")
    public AjaxResult handle(@PathVariable Long id) {
        TronTransaction tronTransaction = tronTransactionService.selectById(id);
        if (tronTransaction == null) {
            return AjaxResult.error("TRON交易不存在");
        }
        if (tronTransaction.getStatus() != 2) {
            return AjaxResult.error("TRON交易未成功");
        }
        if (tronTransaction.getHandle() == 1) {
            return AjaxResult.error("TRON交易未同步");
        }
        if (tronTransaction.getHandle() == 3) {
            return AjaxResult.error("TRON交易已处理");
        }
        jmsProducer.send(QueueConstant.TRON_TRANSACTION_HANDLE_DO, tronTransaction.getHash());
        return success();
    }
}
