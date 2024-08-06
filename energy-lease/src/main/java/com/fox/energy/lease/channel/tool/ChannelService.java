package com.fox.energy.lease.channel.tool;

import com.fox.energy.common.core.domain.tron.EnergyPriceConfig;
import com.fox.energy.conf.domain.AppChannel;

public interface ChannelService {

    /**
     * 通道名称，需要保持唯一
     *
     * @return
     */
    public String getChannel();

    /**
     * 查询能量价格
     * 需按照格式返回价格数据
     *
     * @param channel
     * @return
     */
    EnergyPriceConfig getPrice(AppChannel channel);

    /**
     * 能量租用下单
     *
     * @param channel 通道信息
     * @param orderNo 平台订单号
     * @param hour    租用时长，单位：小时
     * @param number  租用数量
     * @return
     */
    ChannelPayResponse pay(AppChannel channel, String orderNo, int hour, Long number);

    /**
     * 能量租用订单查询
     *
     * @param channel 通道信息
     * @param orderId 通道订单号
     * @return
     */
    ChannelQueryResponse query(AppChannel channel, String orderId);


}
