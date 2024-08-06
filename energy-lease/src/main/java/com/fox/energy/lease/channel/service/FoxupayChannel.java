package com.fox.energy.lease.channel.service;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.fox.energy.common.core.domain.tron.EnergyPriceConfig;
import com.fox.energy.common.utils.StringUtils;
import com.fox.energy.conf.domain.AppChannel;
import com.fox.energy.lease.channel.tool.ChannelPayEnums;
import com.fox.energy.lease.channel.tool.ChannelPayResponse;
import com.fox.energy.lease.channel.tool.ChannelQueryResponse;
import com.fox.energy.lease.channel.tool.ChannelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class FoxupayChannel implements ChannelService {
    private static final Logger logger = LoggerFactory.getLogger(FoxupayChannel.class);

    @Override
    public String getChannel() {
        return "fox_energy";
    }

    @Override
    public EnergyPriceConfig getPrice(AppChannel channel) {
        return price(channel.getAppId());
    }

    private EnergyPriceConfig price(String username) {
        try {
            String url = "https://e.foxupay.com/api/external/config?username=" + username;
            String response = HttpRequest.get(url).execute().body();
            JSONObject resJson = JSONUtil.parseObj(response);
            logger.info("租赁能量 获取价格 返回：{}", resJson.toStringPretty());
            if (resJson.getInt("code") != 200) {
                return null;
            }
            JSONObject ENERGY = resJson.getJSONObject("data");
            EnergyPriceConfig priceConfig = new EnergyPriceConfig();
            priceConfig.setMin(ENERGY.getLong("min"));
            priceConfig.setMax(ENERGY.getLong("max"));
            priceConfig.setCanPay(ENERGY.getBool("canPay"));
            priceConfig.setH1(ENERGY.getBigDecimal("h1"));
            priceConfig.setH24(ENERGY.getBigDecimal("h24"));
            priceConfig.setH72(ENERGY.getBigDecimal("h72"));
            priceConfig.setOther(ENERGY.getBigDecimal("other"));
            return priceConfig;
        } catch (Exception e) {
            return null;
        }
    }


    @Override
    public ChannelPayResponse pay(AppChannel channel, String orderNo, int hour, Long number) {
        return lease(channel.getAppId(), channel.getAppPem(), channel.getAppKey(), orderNo, hour, number);
    }

    private ChannelPayResponse lease(String username, String password, String apiKey, String address, int hour, Long number) {
        JSONObject req = new JSONObject();
        req.set("username", username);
        req.set("password", password);
        req.set("apiKey", apiKey);
        req.set("amount", number);
        if (hour == 1) {
            req.set("time", hour + "h");
        } else {
            req.set("time", (hour / 24) + "d");
        }
        req.set("address", address);
        logger.info("租赁能量 请求：{}", req);
        String response = HttpRequest.post("https://e.foxupay.com/api/external/pay").body(req.toString()).contentType("application/json").execute().body();
        JSONObject resJson = JSONUtil.parseObj(response);
        logger.info("租赁能量 返回：{}", resJson.toStringPretty());
        if (resJson.getInt("code") != 200) {
            return ChannelPayResponse.error(resJson.getStr("msg"));
        }
        String orderNo = resJson.getJSONObject("data").getStr("orderNo");
        return ChannelPayResponse.success(ChannelPayEnums.PENDING, orderNo, "");
    }

    @Override
    public ChannelQueryResponse query(AppChannel channel, String orderId) {
        return queryOrder(orderId);
    }

    private ChannelQueryResponse queryOrder(String orderId) {
        String url = "https://e.foxupay.com/api/external/query?orderNo=" + orderId;
        logger.info("租赁能量 订单查询 请求：{}", url);
        String response = HttpRequest.get(url).execute().body();
        JSONObject resJson = JSONUtil.parseObj(response);
        logger.info("租赁能量 订单查询 返回：{}", resJson.toStringPretty());
        if (resJson.getInt("code") != 200) {
            return ChannelQueryResponse.error(resJson.getStr("msg"));
        }
        JSONObject data = resJson.getJSONObject("data");
        if (data.getInt("status") == 4) {
            return ChannelQueryResponse.success(ChannelPayEnums.FAIL, "", "");
        }
        if (data.getInt("status") == 1 || data.getInt("status") == 2) {
            return ChannelQueryResponse.success(ChannelPayEnums.PENDING, data.getStr("hash"), "");
        }
        return ChannelQueryResponse.success(ChannelPayEnums.SUCCESS, data.getStr("hash"), "");
    }
}
