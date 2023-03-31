package com.spring.jwttoken.security.controller;


import com.spring.jwttoken.security.dto.UserDto;
import com.spring.jwttoken.security.model.AuthRequest;
import com.spring.jwttoken.security.model.User;
import com.spring.jwttoken.security.service.impl.JWTService;
import com.spring.jwttoken.security.service.impl.UserService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
    public User saveUser(@RequestBody @Valid User user){
        User savedUser = userService.addUser(user);

        return savedUser;
    }

    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    @GetMapping("/all")
    public List<UserDto> getAllUser(){
        List<User> userList = userService.getListOfUser();
        ModelMapper modelMapper = new ModelMapper();
        List<UserDto> userDtoList = new ArrayList<>();
        for(User user: userList){
            userDtoList.add(modelMapper.map(user, UserDto.class) );
        }

        return userDtoList;
    }


    @PostMapping("/login")
    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {

        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));
            if (authentication.isAuthenticated()) {

                return jwtService.generateToken(authRequest.getEmail());
            } else {

                return "Invalid credentials!!";
            }
        }
        catch (Exception e){
            return e.getMessage();
        }

    }
}
