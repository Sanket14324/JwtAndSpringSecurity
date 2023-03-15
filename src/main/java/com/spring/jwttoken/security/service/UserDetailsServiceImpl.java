package com.spring.jwttoken.security.service;

import com.spring.jwttoken.security.model.User;
import com.spring.jwttoken.security.model.UserDetail;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public class UserDetailsServiceImpl implements UserDetailsService {


    @Autowired
    private UserService userService;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> userOptional = userService.getUserByEmail(email);
        return userOptional.map(UserDetail::new)
                .orElseThrow(() -> new UsernameNotFoundException("user not found " + email));
    }
}
