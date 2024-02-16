package com.example.springdemotest.controller;

import com.example.springdemotest.model.Session;
import com.example.springdemotest.model.User;
import com.example.springdemotest.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auths")
public class AuthController {

    private final AuthService authService;

    @GetMapping
    public List<Session> getAllSessions() {
        return authService.findAll();
    }

    @PostMapping("/logout/{userId}")
    public boolean logout(@PathVariable Long userId) {
        return authService.logout(userId);
    }

    @PostMapping("/register")
    public boolean register(@RequestBody User user) {
        return authService.register(user);
    }

    @PostMapping("login")
    public Session login(@RequestParam String username, @RequestParam String password) {
        return authService.login(username, password);
    }
}

