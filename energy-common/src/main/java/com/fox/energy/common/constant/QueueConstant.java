package com.fox.energy.common.constant;

/**
 * 消息队列常量
 */
public class QueueConstant {
    public static final String TOPIC_DELAY = "queue.topic.delay";//topic延迟发送
    public static final String LEASE_DO = "queue.lease.do";//执行租赁操作
    public static final String TRON_CALL_DO = "queue.tron.call.do";//监听tron交易
    public static final String TRON_TRANSACTION_DO = "queue.tron.transaction.do";//保存更新tron交易
    public static final String TRON_TRANSACTION_HANDLE_DO = "queue.tron.transaction.handle.do";//交易处理
    public static final String TRON_SWEEP_ADDRESS_IMPORT = "queue.tron.sweep.address.import";//归集导入地址
    public static final String TRON_SWEEP_HANDLE = "queue.tron.sweep.handle";//归集处理
    public static final String TRON_ADDRESS_ACTIVATE = "queue.tron.address.activate";//归集导入地址

}
