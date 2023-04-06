package com.spring.jwttoken.security.repository;

import com.spring.jwttoken.security.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


@SpringBootTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        User user = new User();
        user.setId("hdbuybcdscbuwyg");
        user.setName("Fake name");
        user.setEmail("fake@gmail.com");
        user.setPassword("12345678");
        user.setRole("ADMIN");

        userRepository.save(user);
    }



    @Test
    void findByEmail() {

        Optional<User> actualUser = userRepository.findByEmail("fake@gmail.com");
        assertAll(
                () ->  assertEquals("fake@gmail.com",actualUser.get().getEmail()),
                () ->  assertEquals( "Fake name", actualUser.get().getName()),
                () ->  assertEquals( "ADMIN", actualUser.get().getRole())
        );

    }



    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }
}