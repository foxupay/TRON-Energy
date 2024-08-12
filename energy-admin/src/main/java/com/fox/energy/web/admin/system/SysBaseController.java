package com.fox.energy.web.admin.system;

import cn.hutool.json.JSONObject;
import com.fox.energy.common.core.controller.BaseController;
import com.fox.energy.common.core.domain.AjaxResult;
import com.fox.energy.system.service.ISysConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 系统基础信息
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/system/base")
public class SysBaseController extends BaseController {

    @Autowired
    private ISysConfigService configService;

    /**
     * 系统基础信息
     */
    @GetMapping("/info")
    public AjaxResult info() {
        String name = configService.selectConfigByKey("sys.name");
        String title = configService.selectConfigByKey("sys.title");
        String keywords = configService.selectConfigByKey("sys.keywords");
        String telegram = configService.selectConfigByKey("app.telegram.url");
        String kefuMail = configService.selectConfigByKey("app.kefu.mail");
        JSONObject data = new JSONObject();
        data.set("name", name);
        data.set("title", title);
        data.set("keywords", keywords);
        data.set("telegram", telegram);
        data.set("kefuMail", kefuMail);
        return success(data);
    }

}
