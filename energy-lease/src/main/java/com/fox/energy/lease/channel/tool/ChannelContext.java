package com.fox.energy.lease.channel.tool;

import com.fox.energy.common.core.domain.CommonResult;
import com.fox.energy.common.core.domain.tron.EnergyPriceConfig;
import com.fox.energy.common.exception.ServiceException;
import com.fox.energy.common.utils.StringUtils;
import com.fox.energy.conf.domain.AppChannel;
import com.fox.energy.conf.service.IAppChannelService;
import com.fox.energy.lease.domain.AppLeaseTrade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ChannelContext {
    /**
     * 策略实例集合
     */
    private final ConcurrentHashMap<String, ChannelService> strategyMap = new ConcurrentHashMap<>(20);


    @Autowired
    private IAppChannelService appChannelService;

    /**
     * 注入策略实例
     * 如果使用的是构造器注入，可能会有多个参数注入进来。
     * <p>
     * 如果使用的是field反射注入
     * <p>
     * 如果使用的是setter方法注入，那么你将不能将属性设置为final。
     *
     * @param map 注意注入类型要是Map基础类型
     */
    @Autowired
    public ChannelContext(Map<String, ChannelService> map) {
        if (!CollectionUtils.isEmpty(map)) {
            map.forEach((beanName, payStrategy) -> {
                if (StringUtils.isEmpty(beanName) || payStrategy == null) {
                    return;
                }
                this.strategyMap.put(beanName.toLowerCase(), payStrategy);
            });
        }
    }

    public ChannelService getService(String channelCode) {
        ConcurrentHashMap.KeySetView<String, ChannelService> strings = this.strategyMap.keySet();
        for (String string : strings) {
            if (this.strategyMap.get(string).getChannel().equals(channelCode)) {
                return this.strategyMap.get(string);
            }
        }
        return null;
    }

    public AppChannel getEnableChannel() {
        AppChannel channel = appChannelService.selectEnable();
        if (channel == null) {
            throw new ServiceException("未配置能量通道-E001");
        }
        return channel;
    }

    public ChannelService getEnable() {
        AppChannel channel = appChannelService.selectEnable();
        if (channel == null) {
            throw new ServiceException("未配置能量通道-E001");
        }
        if (CollectionUtils.isEmpty(strategyMap)) {
            throw new ServiceException("未配置能量通道-E002");
        }
        ChannelService service = getService(channel.getChannelCode());
        if (service == null) {
            throw new ServiceException("未配置能量通道-E003");
        }
        return service;
    }

    public EnergyPriceConfig getPrice() {
        AppChannel channel = appChannelService.selectEnable();
        if (channel == null) {
            throw new ServiceException("未配置能量通道-E001");
        }
        if (CollectionUtils.isEmpty(strategyMap)) {
            throw new ServiceException("未配置能量通道-E002");
        }
        ChannelService service = getService(channel.getChannelCode());
        if (service == null) {
            throw new ServiceException("未配置能量通道-E003");
        }
        EnergyPriceConfig price = service.getPrice(channel);
        if (price == null) {
            throw new ServiceException("能量价格获取失败，请稍后再试");
        }
        return price;
    }

    public ChannelPayResponse pay(AppLeaseTrade trade) {
        AppChannel channel = appChannelService.selectByCode(trade.getChannel());
        if (channel == null) {
            throw new ServiceException("未配置能量通道-E001");
        }
        int hour = 0;
        if (trade.getLeaseDurationType() == 1) {
            hour = trade.getLeaseDuration();
        }
        if (trade.getLeaseDurationType() == 2) {
            hour = trade.getLeaseDuration() * 24;
        }
        ChannelService service = getService(channel.getChannelCode());
        if (service == null) {
            throw new ServiceException("未配置能量通道-E003");
        }
        return service.pay(channel, trade.getReceiveAddress(), hour, trade.getResourceValue());
    }

    public ChannelQueryResponse query(AppLeaseTrade trade) {
        AppChannel channel = appChannelService.selectByCode(trade.getChannel());
        if (channel == null) {
            throw new ServiceException("未配置能量通道-E001");
        }
        ChannelService service = getService(channel.getChannelCode());
        if (service == null) {
            throw new ServiceException("未配置能量通道-E003");
        }
        return service.query(channel, trade.getLeaseNo());
    }

}
