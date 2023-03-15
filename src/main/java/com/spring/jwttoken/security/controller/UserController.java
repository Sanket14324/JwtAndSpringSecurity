package com.spring.jwttoken.security.controller;


import com.spring.jwttoken.security.model.AuthRequest;
import com.spring.jwttoken.security.model.User;
import com.spring.jwttoken.security.repository.UserRepository;
import com.spring.jwttoken.security.service.JWTService;
import com.spring.jwttoken.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private JWTService jwtService;

    @PostMapping()
    public User saveUser(@RequestBody User user){
        User savedUser = userService.addUser(user);

        return savedUser;
    }

    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    @GetMapping("/all")
    public List<User> getAllUser(){
        return userService.getListOfUser();
    }


    @PostMapping("/login")
    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(authRequest.getEmail());
        } else {
            throw new UsernameNotFoundException("invalid user request !");
        }


    }
}
