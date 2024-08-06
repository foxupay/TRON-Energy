package com.fox.energy.web.app.version;

import com.fox.energy.app.domain.AppVersion;
import com.fox.energy.app.service.IAppVersionService;
import com.fox.energy.common.annotation.Log;
import com.fox.energy.common.core.controller.BaseController;
import com.fox.energy.common.core.domain.AjaxResult;
import com.fox.energy.common.core.page.TableDataInfo;
import com.fox.energy.common.enums.BusinessType;
import com.fox.energy.common.utils.poi.ExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * APP版本Controller
 *
 * @author IEME
 * @date 2024-05-27
 */
@Api(tags = {"3-APP-软件版本"})
@RestController
@RequestMapping("/app/version")
public class AppsVersionController extends BaseController {
    @Autowired
    private IAppVersionService appVersionService;

    /**
     * 查询APP版本列表
     */
    @ApiOperation("APP-软件版本")
    @GetMapping("/{type}")
    public AjaxResult list(@PathVariable Integer type) {
        AppVersion appVersion = new AppVersion();
        appVersion.setType(type);
        List<AppVersion> list = appVersionService.selectList(appVersion);
        if (list.isEmpty()) {
            return success();
        }
        return success(list.get(0));
    }
}
