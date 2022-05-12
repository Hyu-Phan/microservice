package com.elcom.messaging.rabbitmq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQProperties {
    @Value("${user.rpc.authen.url}")
    public static String USER_RPC_AUTHEN_URL;

    @Value("${user.rpc.exchange}")
    public static String USER_RPC_EXCHANGE;

    @Value("${user.rpc.queue}")
    public static String USER_RPC_QUEUE;

    @Value("${user.rpc.key}")
    public static String USER_RPC_KEY;

    @Value("${report.rpc.url}")
    public static String REPORT_RPC_URL;

    @Value("${report.rpc.exchange}")
    public static String REPORT_RPC_EXCHANGE;

    @Value("${report.rpc.queue}")
    public static String REPORT_RPC_QUEUE;

    @Value("${report.rpc.key}")
    public static String REPORT_RPC_KEY;

//    @Value("${user.rpc.authen.url}")
//    public static String USER_RPC_AUTHEN_URL;
//
//    @Value("${user.rpc.exchange}")
//    public static String USER_RPC_EXCHANGE;
//
//    @Value("${user.rpc.queue}")
//    public static String USER_RPC_QUEUE;
//
//    @Value("${user.rpc.key}")
//    public static String USER_RPC_KEY;
    @Autowired
    public RabbitMQProperties(@Value("${user.rpc.exchange}") String userRpcExchange,
                              @Value("${user.rpc.queue}") String userRpcQueue,
                              @Value("${user.rpc.key}") String userRpcKey,
                              @Value("${user.rpc.authen.url}") String userRpcAuthenUrl,
                              @Value("${report.rpc.exchange}") String reportRpcExchange,
                              @Value("${report.rpc.queue}") String reportRpcQueue,
                              @Value("${report.rpc.key}") String reportRpcKey,
                              @Value("${report.rpc.url}") String reportRpcUrl) {
        USER_RPC_EXCHANGE = userRpcExchange;
        USER_RPC_QUEUE = userRpcQueue;
        USER_RPC_KEY = userRpcKey;
        USER_RPC_AUTHEN_URL = userRpcAuthenUrl;

        REPORT_RPC_EXCHANGE = reportRpcExchange;
        REPORT_RPC_QUEUE = reportRpcQueue;
        REPORT_RPC_KEY = reportRpcKey;
        REPORT_RPC_URL = reportRpcUrl;
    }
}
