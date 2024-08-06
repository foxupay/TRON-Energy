package com.fox.energy.common.enums;

/**
 * 限流类型
 *
 * @author ruoyi
 */

public enum LimitType {
    /**
     * 默认策略全局限流
     */
    DEFAULT,

    /**
     * 根据请求者IP进行限流
     */
    IP,
    /**
     * 根据请求数据进行限流
     */
    DATA,
}
