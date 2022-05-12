package com.elcom.message;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestMessage {
    private String requestMethod;
    private String requestPath;
    private String urlParam;
    private String pathParam;
    private Map<String, String> headerParam;
    private Map<String, Object> bodyParam;

    public String toJsonString() {
        return new Gson().toJson(this);
    }
}
