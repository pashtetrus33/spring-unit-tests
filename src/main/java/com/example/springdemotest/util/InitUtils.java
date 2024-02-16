package com.example.springdemotest.util;

import com.example.springdemotest.model.User;
import com.example.springdemotest.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InitUtils implements CommandLineRunner {

    private final UserService userService;


    @Override
    public void run(String... args) throws Exception {

        userService.save(User.builder()
                .username("misha")
                .password("12345")
                .age(22)
                .build());

        userService.save(User.builder()
                .username("masha")
                .password("password")
                .age(22)
                .build());


        userService.save(User.builder()
                .username("admin")
                .password("87654321")
                .age(22)
                .build());


        userService.save(User.builder()
                .username("user")
                .password("drowssap")
                .age(22)
                .build());
    }
}