package com.elcom.messaging.rabbitmq;

import com.elcom.controller.AuthenController;
import com.elcom.controller.UserController;
import com.elcom.message.RequestMessage;
import com.elcom.message.ResponseMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.util.Map;

public class RpcServer {
    @Autowired
    private UserController userController;

    @Autowired
    private AuthenController authenController;

    @RabbitListener(queues = "${user.rpc.queue}")
    public String processService(String json) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            RequestMessage request = mapper.readValue(json, RequestMessage.class);

            ResponseMessage response = new ResponseMessage(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null);
            if(request != null){
                String requestPath = request.getRequestPath();
                String urlParam = request.getUrlParam();
                String pathParam = request.getPathParam();
                Map<String, Object> bodyParam = request.getBodyParam();
                Map<String, String> headerParam = request.getHeaderParam();

                switch (request.getRequestMethod()) {
                    case "GET":
                        break;
                    case "POST":
                        if("/user".equalsIgnoreCase(requestPath)) {
                            response = userController.createUser(requestPath, request.getRequestMethod(), headerParam, bodyParam);
                        }
                        else if ("/user/login".equalsIgnoreCase(requestPath)) {
                            response = userController.login(requestPath, headerParam, bodyParam);
                        }
                        else if("/user/authentication".equalsIgnoreCase(requestPath)) {

                            response = authenController.authenticate(headerParam);
                        }
                }

                return response != null ? response.toJsonString() : null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

