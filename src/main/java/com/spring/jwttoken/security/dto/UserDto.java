package com.spring.jwttoken.security.dto;

import lombok.Data;

@Data
public class UserDto {
    private String id;

    private String name;

    private String email;

    private  String role;
}
