package com.fox.energy.framework.mq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class JmsProducer {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 发送消息
     *
     * @param destination
     * @param message
     */
    public void send(String destination, Object message) {
        log.info("发送消息到队列:{} ：{}", destination, message);
        rabbitTemplate.convertAndSend("", destination, message);
    }


}
