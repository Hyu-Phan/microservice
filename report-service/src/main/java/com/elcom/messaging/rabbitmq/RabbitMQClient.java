package com.elcom.messaging.rabbitmq;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQClient {
    @Autowired
    private AmqpTemplate amqpTemplate;

    public String callRpcService(String exchangeName, String queueName, String key, String msg) {
        try{
            MessageProperties messageProperties = new MessageProperties();
            messageProperties.setContentType(MessageProperties.CONTENT_TYPE_JSON);
            Message message = new Message(msg.getBytes("UTF-8"), messageProperties);
            String result = (String)amqpTemplate.convertSendAndReceive(exchangeName, key, message);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
