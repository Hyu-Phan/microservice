package com.elcom.service.impl;

import com.elcom.auth.CustomUserDetails;
import com.elcom.model.User;
import com.elcom.repository.UserRepository;
import com.elcom.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService, UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Kiểm tra xem user có tồn tại ko
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User don't exist"));
        return new CustomUserDetails(user);
    }
}
