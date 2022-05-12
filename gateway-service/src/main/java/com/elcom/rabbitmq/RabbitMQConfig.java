package com.elcom.rabbitmq;

import com.elcom.config.GatewayConfig;
import com.elcom.utils.StringUtil;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Configuration
public class RabbitMQConfig {

    @Value("${spring.rabbitmq.host}")
    private String host;

    @Value("${spring.rabbitmq.port}")
    private int port;

    @Value("${spring.rabbitmq.username}")
    private String username;

    @Value("${spring.rabbitmq.password}")
    private String password;

    @Bean("connectionFactory")
    public CachingConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        connectionFactory.setHost(host);
        connectionFactory.setPort(port);

        connectionFactory.setChannelCacheSize(40);
        connectionFactory.setRequestedHeartBeat(60);
        return connectionFactory;
    }

    @Bean
    public RabbitAdmin rabbitAdmin() {
        return new RabbitAdmin(connectionFactory());
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory());
        //rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        rabbitTemplate.setReceiveTimeout(TimeUnit.SECONDS.toMillis(GatewayConfig.TIME_OUT));
        rabbitTemplate.setReplyTimeout(TimeUnit.SECONDS.toMillis(GatewayConfig.TIME_OUT));
        return rabbitTemplate;
    }

    @Bean
    public void initAllExchangeAndQueueAndBinding() {
        List<String> services = GatewayConfig.SERVICES;
        RabbitAdmin rabbitAdmin = rabbitAdmin();
        if(services != null && !services.isEmpty()){
            for(String service : services){
                // RPC
                String rpcQueue = GatewayConfig.SERVICES_MAP.get(service + ".rpc.queue");
                String rpcExchange = GatewayConfig.SERVICES_MAP.get(service + ".rpc.exchange");
                String rpcKey = GatewayConfig.SERVICES_MAP.get(service + ".rpc.key");

                if(!StringUtil.isNullOrEmpty(rpcQueue) && !StringUtil.isNullOrEmpty(rpcExchange) && !StringUtil.isNullOrEmpty(rpcKey )){
                    // Queue
                    Queue queue = new Queue(rpcQueue);
                    rabbitAdmin.declareQueue(queue);

                    // Exchange
                    DirectExchange exchange = new DirectExchange(rpcExchange);
                    rabbitAdmin.declareExchange(exchange);
                    // Binding
                    Binding binding = BindingBuilder.bind(queue).to(exchange).with(rpcKey);
                    rabbitAdmin.declareBinding(binding);
                }

            }
        }
    }
}
