package com.example.springdemotest.service.impl;


import com.example.springdemotest.exceptions.UserIsExistsException;
import com.example.springdemotest.exceptions.UserNotFoundException;
import com.example.springdemotest.model.User;
import com.example.springdemotest.repository.UserRepository;
import com.example.springdemotest.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User save(User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new UserIsExistsException("Username " + user.getUsername() + " is already exists");
        } else {
            log.info("User {} is successfully saved", user.getUsername());
            return userRepository.save(user);
        }
    }

    @Override
    public User updateUser(Long id, User user) {
        User existingUser = findById(id);
        existingUser.setUsername(user.getUsername());
        existingUser.setPassword(user.getPassword());
        existingUser.setAge(user.getAge());
        log.info("User {} is successfully updated", user.getUsername());
        return userRepository.save(existingUser);
    }

    @Override
    public List<User> findAll() {
        log.info("Find all command");
        return userRepository.findAll();
    }

    @Override
    public User findById(Long id) {
        log.info("Find user by id {} ", id);
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    @Override
    public User findByUsername(String username) {
        log.info("Find by username {}", username);
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    @Override
    public void delete(Long id) {
        if (userRepository.findById(id).isPresent()) {
            log.info("User with id {} is successfully deleted", id);
            userRepository.deleteById(id);
        } else {
            log.info("User with id {} is not found", id);
            throw new UserNotFoundException("User is not found");
        }
    }
}
