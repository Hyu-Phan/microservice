package com.elcom.controller;

import com.elcom.message.RequestMessage;
import com.elcom.message.ResponseMessage;
import com.elcom.messaging.rabbitmq.RabbitMQClient;
import com.elcom.messaging.rabbitmq.RabbitMQProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Controller
public class BaseController {
    private final Logger logger = LoggerFactory.getLogger(BaseController.class);
    @Autowired
    private RabbitMQClient rabbitMQClient;

    public ResponseMessage authenToken(Map<String, String> headerParam) {
        RequestMessage userRpcRequest = new RequestMessage();
        userRpcRequest.setRequestMethod("POST");
        userRpcRequest.setRequestPath(RabbitMQProperties.USER_RPC_AUTHEN_URL);
        userRpcRequest.setUrlParam(null);
        userRpcRequest.setBodyParam(null);
        userRpcRequest.setHeaderParam(headerParam);
        logger.info(RabbitMQProperties.USER_RPC_EXCHANGE + " " +
                RabbitMQProperties.USER_RPC_QUEUE + " " +  RabbitMQProperties.USER_RPC_KEY);

        String result = rabbitMQClient.callRpcService(RabbitMQProperties.USER_RPC_EXCHANGE,
                RabbitMQProperties.USER_RPC_QUEUE, RabbitMQProperties.USER_RPC_KEY, userRpcRequest.toJsonString());
        if(result != null) {
            ObjectMapper mapper = new ObjectMapper();
            ResponseMessage response = null;
            try {
                response = mapper.readValue(result, ResponseMessage.class);
                return response;
            }catch (Exception e){
                return null;
            }
        }
        else{
            return null;
        }
    }
}
