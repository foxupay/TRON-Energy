package com.fox.energy.framework.mq;

import cn.hutool.json.JSONObject;
import com.fox.energy.common.constant.QueueConstant;
import org.apache.activemq.ScheduledMessage;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.jms.Message;


@Service
public class JmsProducer {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Resource
    private JmsTemplate jmsTemplate;

    /**
     * 发送消息
     *
     * @param destination
     * @param message
     */
    public void send(String destination, Object message) {
        log.info("发送消息到队列:{} ：{}", destination, message);
        jmsTemplate.convertAndSend(new ActiveMQQueue(destination), message);
    }

    /**
     * 广播消息
     *
     * @param destination
     * @param message
     */
    public void sendTopic(String destination, Object message) {
        log.info("发送消息到队列:{} ：{}", destination, message);
        jmsTemplate.convertAndSend(new ActiveMQTopic(destination), message);
    }

    public void sendDelayTopic(String destination, String message, long delay) {
        log.info("发送消息到队列:{} ：{}", destination, message);
        JSONObject data = new JSONObject();
        data.set("topic", destination);
        data.set("content", message);
        sendDelay(QueueConstant.TOPIC_DELAY, data.toString(), delay);
    }

    /**
     * 发送延时消息
     *
     * @param destination
     * @param message
     * @param delay
     */
    public void sendDelay(String destination, String message, long delay) {
        log.info("发送延时消息到队列:{} ：{} ：{}", destination, message, delay);
        //延迟队列，延迟time毫秒
        //延迟队列需要在 <broker>标签上增加属性 schedulerSupport="true"
        jmsTemplate.send(destination, session -> {
            Message msg = session.createTextMessage(message);
            msg.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_DELAY, delay);
            return msg;
        });
    }

}
