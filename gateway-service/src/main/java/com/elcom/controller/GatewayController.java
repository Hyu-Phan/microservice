package com.elcom.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
public class GatewayController extends BaseController{

    // GET
    @RequestMapping(value = "**", method = RequestMethod.GET)
    public ResponseEntity<String> getMethod(@RequestParam Map<String, String> reqParam,
                                            @RequestHeader Map<String, String> headers, HttpServletRequest req) throws JsonProcessingException{
        return processRequest("GET", reqParam, null, headers, req);
    }

    // POST
    @RequestMapping(value = "**", method = RequestMethod.POST)
    public ResponseEntity<String> postMethod(@RequestParam Map<String, String> reqParam,
                                            @RequestHeader Map<String, String> headers,
                                             @RequestBody(required = false) Map<String, Object> requestBody,
                                             HttpServletRequest req) throws JsonProcessingException{
        return processRequest("POST", reqParam, requestBody, headers, req);
    }

    // PUT
    @RequestMapping(value = "**", method = RequestMethod.PUT)
    public ResponseEntity<String> putMethod(@RequestParam Map<String, String> reqParam,
                                             @RequestHeader Map<String, String> headers,
                                             @RequestBody(required = false) Map<String, Object> requestBody,
                                             HttpServletRequest req) throws JsonProcessingException{
        return processRequest("PUT", reqParam, requestBody, headers, req);
    }

    // DELETE
    @RequestMapping(value = "**", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteMethod(@RequestParam Map<String, String> reqParam,
                                             @RequestHeader Map<String, String> headers,
                                             @RequestBody(required = false) Map<String, Object> requestBody,
                                             HttpServletRequest req) throws JsonProcessingException{
        return processRequest("DELETE", reqParam, requestBody, headers, req);
    }
}
