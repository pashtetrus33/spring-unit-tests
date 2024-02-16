package com.example.springdemotest.service;

import com.example.springdemotest.model.Session;
import com.example.springdemotest.model.User;

import java.util.List;

public interface AuthService {

    Session login(String username, String password);

    boolean register(User user);

    boolean logout(Long userId);

    List<Session> findAll();
}
