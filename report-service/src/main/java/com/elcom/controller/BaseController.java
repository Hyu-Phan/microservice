package com.elcom.controller;

import com.elcom.message.RequestMessage;
import com.elcom.message.ResponseMessage;
import com.elcom.messaging.rabbitmq.RabbitMQClient;
import com.elcom.messaging.rabbitmq.RabbitMQProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

public class BaseController {
    @Autowired
    private RabbitMQClient rabbitMQClient;

    public ResponseMessage authenToken(Map<String, String> headerParam) {
        RequestMessage userRpcRequest = new RequestMessage();
        userRpcRequest.setRequestMethod("POST");
        userRpcRequest.setRequestPath(RabbitMQProperties.USER_RPC_AUTHEN_URL);
        userRpcRequest.setUrlParam(null);
        userRpcRequest.setBodyParam(null);
        userRpcRequest.setHeaderParam(headerParam);

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
