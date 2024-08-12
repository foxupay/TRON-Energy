package com.fox.energy.framework.mq;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.RedeliveryPolicy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;

/**
 * 描述:
 * activemq 有两种模式 queue 和 topic
 * queue 模式是单对单，有多个消费者的情况下则是使用轮询监听
 * topic 模式/广播模式/发布订阅模式 是一对多，发送消息所有的消费者都能够监听到
 *
 * @author gblfy
 * @date 2022-11-02
 */
@EnableJms
@Configuration
public class ActiveMQConfig {

    @Value("${spring.activemq.user}")
    private String username;
    @Value("${spring.activemq.password}")
    private String password;
    @Value("${spring.activemq.broker-url}")
    private String brokerUrl;


    @Bean
    public ActiveMQConnectionFactory connectionFactory() {
        return new ActiveMQConnectionFactory(username, password, brokerUrl);
    }

    @Bean
    public JmsListenerContainerFactory<?> jmsListenerContainerQueue(ActiveMQConnectionFactory connectionFactory) {
        DefaultJmsListenerContainerFactory bean = new DefaultJmsListenerContainerFactory();
        // 关闭Session事务，手动确认与事务冲突
        bean.setSessionTransacted(false);
        // 设置消息的签收模式（自己签收）
        /**
         * AUTO_ACKNOWLEDGE = 1 ：自动确认
         * CLIENT_ACKNOWLEDGE = 2：客户端手动确认
         * DUPS_OK_ACKNOWLEDGE = 3： 自动批量确认
         * SESSION_TRANSACTED = 0：事务提交并确认
         * 但是在activemq补充了一个自定义的ACK模式:
         * INDIVIDUAL_ACKNOWLEDGE = 4：单条消息确认
         **/
        bean.setSessionAcknowledgeMode(4);
        //此处设置消息重发规则，redeliveryPolicy() 中定义
        connectionFactory.setRedeliveryPolicy(redeliveryPolicy());
        bean.setConnectionFactory(connectionFactory);
        return bean;
    }

    @Bean
    public JmsListenerContainerFactory<?> jmsListenerContainerTopic(ActiveMQConnectionFactory connectionFactory) {
        DefaultJmsListenerContainerFactory bean = new DefaultJmsListenerContainerFactory();
        // 关闭Session事务，手动确认与事务冲突
        bean.setSessionTransacted(false);
        bean.setSessionAcknowledgeMode(4);
        //设置为发布订阅方式, 默认情况下使用的生产消费者方式
        bean.setPubSubDomain(true);
        bean.setConnectionFactory(connectionFactory);
        return bean;
    }

    /**
     * 消息的重发规则配置
     */
    @Bean
    public RedeliveryPolicy redeliveryPolicy() {
        RedeliveryPolicy redeliveryPolicy = new RedeliveryPolicy();
        // 是否在每次尝试重新发送失败后,增长这个等待时间
        redeliveryPolicy.setUseExponentialBackOff(true);
        // 重发次数五次， 总共六次
        redeliveryPolicy.setMaximumRedeliveries(5);
        // 重发时间间隔,默认为1000ms（1秒）
        redeliveryPolicy.setInitialRedeliveryDelay(1000);
        // 重发时长递增的时间倍数2
        redeliveryPolicy.setBackOffMultiplier(2);
        // 是否避免消息碰撞
        redeliveryPolicy.setUseCollisionAvoidance(false);
        // 设置重发最大拖延时间-1表示无延迟限制
        redeliveryPolicy.setMaximumRedeliveryDelay(-1);
        return redeliveryPolicy;
    }
}