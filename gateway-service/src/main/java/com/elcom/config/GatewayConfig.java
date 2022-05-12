package com.elcom.config;

import com.elcom.dto.RabbitMQType;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.util.*;

public class GatewayConfig {
    public static List<String> SERVICES = new ArrayList<>();

    public static final Map<String, String> SERVICES_MAP = new HashMap<>();

    public static final Map<String, List<String>> SERVICE_PATH_MAP = new HashMap<>();

    public static final Map<String, String> RABBIT_TYPE_MAP = new HashMap<>();

    public static Integer TIME_OUT = 15;

    public static final String PATH = System.getProperty("user.dir") + File.separator + "gateway-service" + File.separator + "config";

    static {
        loadConfig();
    }
    private static void loadConfig() {
        try {
            Properties properties = new Properties();
            properties.load(new InputStreamReader(new FileInputStream(PATH + File.separator + "gateway.properties"), "UTF-8"));
            Enumeration e = properties.propertyNames();

            while(e.hasMoreElements()) {
                String key = (String) e.nextElement();
                String value = properties.getProperty(key);

                if (key.equals("services")) {
                    String[] services = value.split(",");
                    SERVICES = Arrays.asList(services);
                }
                else {
                    SERVICES_MAP.put(key, value);
                    if(key.contains(".path.rabbit.file")) {
                        loadRabbitJson(value);
                    }
                    else if(key.contains(".path")){
                        String[] paths = value.split(",");
                        List<String> pathList = Arrays.asList(paths);
                        SERVICE_PATH_MAP.put(key.replace(".path", ""), pathList);
                    }
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    private static void loadRabbitJson(String jsonFile) {
        try{
            FileReader reader = new FileReader(PATH  + File.separator + jsonFile);
            ObjectMapper mapper = new ObjectMapper();
            List<RabbitMQType> rabbitMQTypes = mapper.readValue(reader, new TypeReference<List<RabbitMQType>>() {
            });
            if(rabbitMQTypes != null && rabbitMQTypes.size()>0) {
                rabbitMQTypes.forEach((tmp) -> {
//                    System.out.println(tmp.getMethod() + " " + tmp.getPath() + " " + tmp.getRabbit());
                    RABBIT_TYPE_MAP.put(tmp.getMethod() + " " + tmp.getPath(), tmp.getRabbit());
                });
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        loadConfig();
        for(String key : SERVICES_MAP.keySet()){
            System.out.println(key + " : " + SERVICES_MAP.get(key));
        }
    }
}
