package com.fox.energy.tron.controller;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.fox.energy.common.annotation.APILog;
import com.fox.energy.common.constant.QueueConstant;
import com.fox.energy.common.core.controller.BaseController;
import com.fox.energy.common.core.domain.tron.TronMonitorModel;
import com.fox.energy.common.utils.http.HttpCommonUtil;
import com.fox.energy.framework.mq.JmsProducer;
import com.fox.energy.tron.domain.TronAddress;
import com.fox.energy.tron.service.ITronAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 监控回调
 *
 * @author IEME
 * @date 2024-04-30
 */
@RestController
@RequestMapping("/tron/monitor")
public class TronMonitorCallController extends BaseController {

    @Autowired
    private ITronAddressService tronAddressService;

    @Autowired
    private JmsProducer jmsProducer;

    @APILog(title = "收款交易-回调")
    @PostMapping({"/call"})
    public String call() {
        String jsonData = HttpCommonUtil.getJSONDataFromRequest();
        logger.info("回调数据：{}", jsonData);
        JSONObject resJson = JSONUtil.parseObj(jsonData);

        TronMonitorModel model = JSONUtil.toBean(resJson.toString(), TronMonitorModel.class);

        switch (model.getType()) {
            case "trx": {
                TronAddress tronAddress = tronAddressService.selectByAddress(model.getAddress());
                if (tronAddress == null) {
                    return "SUCCESS";
                }
                jmsProducer.send(QueueConstant.TRON_CALL_DO, JSONUtil.toJsonStr(model));
            }
            case "contract": {
                TronAddress tronAddress = tronAddressService.selectByAddress(model.getAddress());
                if (tronAddress == null) {
                    return "SUCCESS";
                }
                if (!model.isUSDT()) {
                    return "SUCCESS";
                }
                model.setType("usdt");
                jmsProducer.send(QueueConstant.TRON_CALL_DO, JSONUtil.toJsonStr(model));
            }
            case "resource": {
                return "SUCCESS";
            }
            default: {
                return "SUCCESS";
            }
        }
    }

}
