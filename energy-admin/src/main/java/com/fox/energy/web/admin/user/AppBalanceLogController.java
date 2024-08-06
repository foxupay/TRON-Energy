package com.fox.energy.web.admin.user;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.fox.energy.common.utils.StringUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fox.energy.common.annotation.Log;
import com.fox.energy.common.core.controller.BaseController;
import com.fox.energy.common.core.domain.AjaxResult;
import com.fox.energy.common.enums.BusinessType;
import com.fox.energy.user.domain.AppBalanceLog;
import com.fox.energy.user.service.IAppBalanceLogService;
import com.fox.energy.common.utils.poi.ExcelUtil;
import com.fox.energy.common.core.page.TableDataInfo;

/**
 * 用户余额记录Controller
 *
 * @author IEME
 * @date 2024-05-13
 */
@RestController
@RequestMapping("/user/log")
public class AppBalanceLogController extends BaseController {
    @Autowired
    private IAppBalanceLogService appBalanceLogService;

    /**
     * 查询用户余额记录列表
     */
    @PreAuthorize("@ss.hasPermi('user:log:list')")
    @GetMapping("/list")
    public TableDataInfo list(AppBalanceLog appBalanceLog) {
        startPage();
        if (StringUtils.isEmpty(appBalanceLog.getUserId())) {
            return getDataTable(new ArrayList<>());
        }
        List<AppBalanceLog> list = appBalanceLogService.selectList(appBalanceLog);
        return getDataTable(list);
    }
}
