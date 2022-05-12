package com.elcom.controller;

import com.elcom.config.GatewayConfig;
import com.elcom.exception.ValidationException;
import com.elcom.message.RequestMessage;
import com.elcom.message.ResponseMessage;
import com.elcom.rabbitmq.RabbitMQClient;
import com.elcom.utils.StringUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.Map;

public class BaseController {
    @Autowired
    private RabbitMQClient rabbitMQClient;

    public ResponseEntity<String> processRequest(String requestMethod, Map<String, String> urlParamMap,
                                                  Map<String, Object> bodyParamMap, Map<String, String> headerParamMap,
                                                  HttpServletRequest req) throws JsonProcessingException {
        // Get all value
        String requestPath = req.getRequestURI();
        String urlParam  = generateMapString(urlParamMap);
        String pathParam = null;

        // Get service
        int index = requestPath.indexOf("/", 1);
        String service = null;
        if(index != -1) {
            service = requestPath.substring(1, index);
        }
        else {
            service = requestPath.substring(1);
        }
        // Check Path Param
        int lastIndex = requestPath.lastIndexOf("/");
        if(lastIndex != -1) {
            String lastStr = requestPath.substring(lastIndex + 1);
            if(StringUtil.isNumberic(lastStr) || StringUtil.isUUID(lastStr)) {
                requestPath = requestPath.substring(0,lastIndex);
                pathParam = lastStr;
            }
        }
        RequestMessage request = new RequestMessage(requestMethod, requestPath, urlParam, pathParam, headerParamMap, bodyParamMap);
        String result = null;
        System.out.println(requestPath);
        // Get rabbit type
        String rabbitType = GatewayConfig.RABBIT_TYPE_MAP.get(requestMethod + " " + requestPath);
        if("rpc".equalsIgnoreCase(rabbitType)){
            String rpcQueue = GatewayConfig.SERVICES_MAP.get(service + ".rpc.queue");
            String rpcExchange = GatewayConfig.SERVICES_MAP.get(service + ".rpc.exchange");
            String rpcKey = GatewayConfig.SERVICES_MAP.get(service + ".rpc.key");
            if(StringUtil.isNullOrEmpty(rpcQueue) || StringUtil.isNullOrEmpty(rpcExchange) || StringUtil.isNullOrEmpty(rpcKey)){
                throw new ValidationException("Không tim thấy rabbit mq cho service" + service);
            }
            result = rabbitMQClient.callRpcService(rpcExchange, rpcQueue, rpcKey, request.toJsonString());
        }
        if(result != null) {
            ObjectMapper mapper = new ObjectMapper();
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            mapper.setDateFormat(df);
            ResponseMessage response = mapper.readValue(result, ResponseMessage.class);
            return new ResponseEntity(response, HttpStatus.OK);
//            return new ResponseEntity(response.getData(), HttpStatus.valueOf(response.getStatus()));
        }

        ResponseMessage responseMessage = new ResponseMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.toString(), null);
        result = responseMessage.toJsonString();
        return new ResponseEntity(result, HttpStatus.OK);
    }

    String generateMapString(Map<String, String> map) {
        StringBuilder builder = new StringBuilder();
        Iterator<Map.Entry<String, String>> iterator = map.entrySet().iterator();
        while(iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            builder.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }
        String result = builder.toString();
        return result != null && result.endsWith("&") ? result.substring(0, result.length() - 1) : result;
    }
}
