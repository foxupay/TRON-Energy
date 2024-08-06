package com.fox.energy.lease.server;

import cn.hutool.json.JSONUtil;
import com.fox.energy.common.constant.CacheConstants;
import com.fox.energy.common.core.domain.tron.EnergyPriceConfig;
import com.fox.energy.common.core.redis.RedisCache;
import com.fox.energy.common.enums.TronToken;
import com.fox.energy.common.utils.StringUtils;
import com.fox.energy.common.utils.spring.SpringUtils;
import com.fox.energy.tron.util.TronSupport;
import com.fox.energy.lease.channel.tool.ChannelContext;
import com.fox.energy.system.service.ISysConfigService;
import com.fox.energy.user.domain.AppProxy;
import com.fox.energy.user.service.IAppProxyService;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

public class TronPriceServer {

    private static ISysConfigService sysConfigService = SpringUtils.getBean(ISysConfigService.class);
    private static RedisCache redisCache = SpringUtils.getBean(RedisCache.class);
    private static IAppProxyService appProxyService = SpringUtils.getBean(IAppProxyService.class);
    private static ChannelContext channelContext = SpringUtils.getBean(ChannelContext.class);

    public static EnergyPriceConfig price() {
        return price(null);
    }


    public static EnergyPriceConfig price(Integer userId) {
        EnergyPriceConfig price;
        String key = CacheConstants.LEASE_PRICE_KEY.key() + "origin:" + channelContext.getEnable().getChannel();
        if (redisCache.hasKey(key)) {
            price = JSONUtil.toBean(redisCache.getCacheObject(key).toString(), EnergyPriceConfig.class);
        } else {
            price = channelContext.getPrice();
            if (price == null) {
                return null;
            }
            //缓存原始价格
            redisCache.setCacheObject(key, JSONUtil.toJsonStr(price), 30, TimeUnit.SECONDS);
        }
        if (userId == null) {
            String rateStr = sysConfigService.getRealByKey("app.price.rate");
            if (StringUtils.isEmpty(rateStr)) {
                return null;
            }
            return initPrice(price, new BigDecimal(rateStr));
        }
        //查询是否有单独费率
        AppProxy appProxy = appProxyService.selectByUserId(userId);
        if (appProxy != null) {
            return initPrice(price, appProxy.getRate());
        }
        String rateStr = sysConfigService.getRealByKey("app.price.rate");
        if (StringUtils.isEmpty(rateStr)) {
            return null;
        }
        return initPrice(price, new BigDecimal(rateStr));
    }

    public static EnergyPriceConfig initPrice(EnergyPriceConfig price, BigDecimal rate) {
        price.setH1(price.getH1().multiply(rate));
        price.setH24(price.getH24().multiply(rate));
        price.setH72(price.getH72().multiply(rate));
        price.setOther(price.getOther().multiply(rate));
        return price;
    }


    public static BigDecimal rate() {
        String key = CacheConstants.LEASE_RATE_KEY.key();
        if (redisCache.hasKey(key)) {
            return new BigDecimal(redisCache.getCacheObject(key).toString());
        }
        BigDecimal price = TronSupport.getPrice(TronToken.USDT);
        if (price == null || price.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }
        redisCache.setCacheObject(key, price.toPlainString(), 5, TimeUnit.MINUTES);
        return price;
    }

}
