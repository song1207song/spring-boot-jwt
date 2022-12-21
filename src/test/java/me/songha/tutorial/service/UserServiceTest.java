package me.songha.tutorial.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void password_encode() {
        String encodedPassword = passwordEncoder.encode("1234");
        System.out.println(encodedPassword);
    }
}