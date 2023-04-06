package com.spring.jwttoken.security.controller;


import com.spring.jwttoken.security.dto.UserDto;
import com.spring.jwttoken.security.model.AuthRequest;
import com.spring.jwttoken.security.model.User;
import com.spring.jwttoken.security.service.impl.JWTService;
import com.spring.jwttoken.security.service.impl.UserServiceImpl;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Autowired
    private JWTService jwtService;

    @PostMapping()
    public ResponseEntity<Object> saveUser(@RequestBody @Valid User user){
        User savedUser = userServiceImpl.addUser(user);

        return ResponseEntity.status(HttpStatusCode.valueOf(201)).body(savedUser);
    }

    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    @GetMapping("/all")
    public List<UserDto> getAllUser(){
        List<User> userList = userServiceImpl.getListOfUser();
        ModelMapper modelMapper = new ModelMapper();
        List<UserDto> userDtoList = new ArrayList<>();
        for(User user: userList){
            userDtoList.add(modelMapper.map(user, UserDto.class) );
        }

        return userDtoList;
    }

    @PreAuthorize("hasAnyAuthority('SUPER_ADMIN', 'ADMIN')")
    @GetMapping("/{credentials}")
    public ResponseEntity<Object> getUserByEmailOrId(@PathVariable String credentials){

        User user = userServiceImpl.getUserByEmailOrId(credentials);

        ModelMapper modelMapper = new ModelMapper();

        UserDto userDto = modelMapper.map(user, UserDto.class);
        return ResponseEntity.ok(userDto);

    }

    @PreAuthorize("hasAnyAuthority('SUPER_ADMIN', 'USER')")
    @PutMapping("/{id}")
    public ResponseEntity<Object> putUserById(@PathVariable String id, @RequestBody User user){
        User editedUser = userServiceImpl.editUserById(id, user);
        ModelMapper modelMapper = new ModelMapper();
        UserDto userDto = modelMapper.map(editedUser, UserDto.class);
        return ResponseEntity.ok(userDto);

    }


    @PreAuthorize("hasAnyAuthority('SUPER_ADMIN', 'USER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUserById(@PathVariable String id){

        User deletedUser = userServiceImpl.deleteUserById(id);
        ModelMapper modelMapper = new ModelMapper();
        UserDto userDto = modelMapper.map(deletedUser, UserDto.class);

        return ResponseEntity.ok(userDto);

    }


    @PostMapping("/login")
    public String authenticateAndGetToken(@RequestBody @Valid AuthRequest authRequest) throws ExpiredJwtException {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));
            if (authentication.isAuthenticated()) {

                return jwtService.generateToken(authRequest.getEmail());
            }

            return "Invalid credentials.";
    }
}
