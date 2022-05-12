package com.elcom.messaging.rabbitmq;

import com.elcom.controller.ReportController;
import com.elcom.message.RequestMessage;
import com.elcom.message.ResponseMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.util.Map;

public class RpcServer {
    @Autowired
    private ReportController reportController;

    @RabbitListener(queues = "${report.rpc.queue}")
    public String processService(String json) {
        try{
            ObjectMapper mapper = new ObjectMapper();
            RequestMessage request = mapper.readValue(json, RequestMessage.class);
            ResponseMessage response = new ResponseMessage(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null);
            if(request != null) {
                String requestPath = request.getRequestPath();
                String urlParam = request.getUrlParam();
                String pathParam = request.getPathParam();
                Map<String, String> headerParam = request.getHeaderParam();
                Map<String, Object> bodyParam = request.getBodyParam();
                switch (request.getRequestMethod()){
                    case "GET":
                        break;
                    case "POST":
                        if("/report/sync".equalsIgnoreCase(requestPath)){
                            reportController.addBook(headerParam, bodyParam);
                        }
                }
            }
            return response != null ? response.toJsonString() : null;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
