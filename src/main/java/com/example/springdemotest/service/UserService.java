package com.example.springdemotest.service;

import com.example.springdemotest.model.User;

import java.util.List;

public interface UserService {

    User save(User user);

    List<User> findAll();

    void delete(Long id);

    User findByUsername(String username);

    User findById(Long id);

    User updateUser(Long id, User user);
}
