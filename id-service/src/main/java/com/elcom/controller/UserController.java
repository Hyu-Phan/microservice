package com.elcom.controller;

import com.elcom.auth.CustomUserDetails;
import com.elcom.auth.jwt.JwtTokenProvider;
import com.elcom.message.ResponseMessage;
import com.elcom.model.User;
import com.elcom.repository.UserRepository;
import com.elcom.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Controller
public class UserController {
    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private AuthenticationManager authenticationManager;


    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public ResponseMessage createUser(String requestUrl, String method, Map<String, String> headerParam, Map<String, Object> bodyParam) {
        ResponseMessage response = null;
        String userName = (String) bodyParam.get("username");
        String password = (String) bodyParam.get("password");
        if(userRepository.existsByUsername(userName)) {
            response = new ResponseMessage(HttpStatus.CONFLICT.value(), "Đã tồn tại tài khoản trên hệ thống", null);
        }
        else {
            User user = new User();
            user.setUsername(userName);
            user.setPassword(passwordEncoder.encode(password));
            userRepository.save(user);
            response = new ResponseMessage(HttpStatus.OK.value(), "Đăng kí tài khoản thành công",null);
        }
        return response;
    }

    public ResponseMessage login(String requestPath, Map<String, String> headerParam, Map<String, Object> bodyParam) {
        logger.info("call login");
        ResponseMessage response = null;
        if(bodyParam == null || bodyParam.isEmpty()) {
            response = new ResponseMessage(HttpStatus.BAD_REQUEST.value(), "Vui lòng nhập username và password", null);
        }
        else {
            String username = (String) bodyParam.get("username");
            String password = (String) bodyParam.get("password");
            if(StringUtil.isNullOrEmpty(username) || StringUtil.isNullOrEmpty(password)){
                response = new ResponseMessage(HttpStatus.BAD_REQUEST.value(), "Vui lòng nhập đầy đủ thông tin", null);
            }
            else {
                try{
                    Authentication authentication = authenticationManager
                            .authenticate(new UsernamePasswordAuthenticationToken(username, password));
                    SecurityContextHolder.getContext().setAuthentication(authentication);

                    CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

                    String jwt = jwtTokenProvider.createToken(userDetails);
                    response = new ResponseMessage(HttpStatus.OK.value(), "Đăng nhập thành công", jwt);
                }catch (Exception e){
                    response = new ResponseMessage(HttpStatus.BAD_REQUEST.value(), "Thông tin tài khoản không chính xác", null);
                }

            }
        }
        return response;
    }
}
