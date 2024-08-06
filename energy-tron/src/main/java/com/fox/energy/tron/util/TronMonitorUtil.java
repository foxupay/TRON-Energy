package com.fox.energy.tron.util;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.fox.energy.common.core.domain.CommonResult;
import com.fox.energy.system.service.ISysConfigService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TronMonitorUtil {

    private Logger logger = org.slf4j.LoggerFactory.getLogger(TronMonitorUtil.class);
    @Autowired
    private ISysConfigService sysConfigService;

    public CommonResult addAddress(String address) {
        String url = "https://monitor.foxupay.com/api/addr/add";
        JSONObject req = new JSONObject();
        req.set("address", address);
        req.set("webhook", sysConfigService.getRealByKey("sys.domain") + "/api/tron/monitor/call");
        logger.info("添加地址监控  请求:{}", req.toStringPretty());
        String body = HttpRequest.post(url)
                .body(req.toString())
                .header("FOX-API-KEY", sysConfigService.getRealByKey("app.monitor.key"))
                .contentType("application/json")
                .execute().body();
        JSONObject response = JSONUtil.parseObj(body);
        logger.info("添加地址监控  返回:{}", response.toStringPretty());
        if (response.getInt("code") == 200) {
            return CommonResult.success();
        }
        return CommonResult.error(response.getStr("msg"));
    }

}
