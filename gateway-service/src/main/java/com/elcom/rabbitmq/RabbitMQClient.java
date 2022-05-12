package com.elcom.rabbitmq;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQClient {
    @Autowired
    private AmqpAdmin amqpAdmin;

    @Autowired
    private AmqpTemplate amqpTemplate;

    public String callRpcService(String exchangeName, String queueName, String key, String msg){
        try{
            Queue queue = new Queue(queueName);
            amqpAdmin.declareQueue(queue);
            // Exchange
            DirectExchange exchange = new DirectExchange(exchangeName);
            amqpAdmin.declareExchange(exchange);
            // Binding
            Binding binding = BindingBuilder.bind(queue).to(exchange).with(key);
            amqpAdmin.declareBinding(binding);

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
