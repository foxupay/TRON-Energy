package com.fox.energy.web.app.tron;

import cn.hutool.http.HttpRequest;
import com.fox.energy.common.core.controller.BaseController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 监控回调
 *
 * @author IEME
 * @date 2024-04-30
 */
@RestController
@RequestMapping("/tron/grid")
public class TronGridController extends BaseController {

    @PostMapping({""})
    public Object call(@RequestBody TronGridModel model) {
        if ("GET".equalsIgnoreCase(model.getMethod())) {
            return HttpRequest.get(model.getUrl())
                    .body(model.getParams())
                    .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/124.0.0.0 Safari/537.36")
                    .execute().body();
        }
        if ("POST".equalsIgnoreCase(model.getMethod())) {
            return HttpRequest.post(model.getUrl())
                    .body(model.getParams())
                    .contentType("application/json")
                    .execute().body();
        }
        return null;
    }
}

class TronGridModel {
    private String url;
    private String method;
    private String params;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }
}