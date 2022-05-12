package com.elcom.controller;

import com.elcom.auth.jwt.JwtTokenProvider;
import com.elcom.message.ResponseMessage;
import com.elcom.service.impl.AuthServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;

import java.util.Map;

@Controller
public class AuthenController {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private AuthServiceImpl authService;


    public ResponseMessage authenticate(Map<String, String> headerParam) {
        ResponseMessage response = null;
        if(headerParam == null || headerParam.isEmpty()) {
            response = new ResponseMessage(HttpStatus.UNAUTHORIZED.value(), "Vui lòng đăng nhập", null);
        }
        else {
            String bearerToken = headerParam.get("authorization");
            if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")){
                try{
                    String jwt = bearerToken.substring(7);
                    String username = jwtTokenProvider.getUsernameFromJWT(jwt);

                    UserDetails userDetails = authService.loadUserByUsername(username);
                    if(userDetails != null) {
                        UsernamePasswordAuthenticationToken authentication =
                                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                        response = new ResponseMessage(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), null);
                    }
                    else {
                        response = new ResponseMessage(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase(), null);
                    }
                }catch (Exception e){
                    response = new ResponseMessage(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase(), null);
                }
            }
            else {
                response = new ResponseMessage(HttpStatus.UNAUTHORIZED.value(), "Vui lòng đăng nhập", null);
            }
        }
        return response;
    }
}
