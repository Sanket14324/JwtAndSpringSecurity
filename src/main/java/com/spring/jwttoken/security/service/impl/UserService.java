package com.spring.jwttoken.security.service.impl;

import com.spring.jwttoken.security.model.User;
import com.spring.jwttoken.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {


    @Autowired
    private UserRepository userRepository;


    @Autowired
    private PasswordEncoder passwordEncoder;

    public User addUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return user;
    }

    public List<User> getListOfUser(){
        List<User> userList = userRepository.findAll();
        return userList;
    }

    public Optional<User> getUserByEmail(String email){
        Optional<User> userOptional = userRepository.findByEmail(email);

        return userOptional;
    }
}
