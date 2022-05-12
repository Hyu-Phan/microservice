package com.elcom.messaging.rabbitmq;

import com.elcom.utils.StringUtil;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQClient {
    @Autowired
    private AmqpAdmin amqpAdmin;

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    @Qualifier("directAutoDeleteQueue")
    private Queue directAutoDeleteQueue;

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
