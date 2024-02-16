package com.example.springdemotest.service.impl;

import com.example.springdemotest.exceptions.CredentialsAreWrongException;
import com.example.springdemotest.exceptions.SessionNotFoundException;
import com.example.springdemotest.exceptions.UserIsExistsException;
import com.example.springdemotest.exceptions.UserNotFoundException;
import com.example.springdemotest.model.Session;
import com.example.springdemotest.model.User;
import com.example.springdemotest.repository.SessionRepository;
import com.example.springdemotest.repository.UserRepository;
import com.example.springdemotest.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;

    @Override
    public Session login(String username, String password) {
        if (userRepository.findByUsername(username).isPresent()) {
            User user = userRepository.findByUsername(username).get();
            if (password.equals(user.getPassword())) {

                if (user.getSession() != null) {
                    Session session = user.getSession();
                    if (LocalDateTime.now().isBefore(session.getCreatedAt().plusMinutes(2))) {
                        log.info("Session is found and still active {}", session.getToken());
                        return session;
                    }
                    log.info("Session is found but is expired {}", session);
                    log.info("Creating new one....");
                }
                Session session = Session.builder()
                        .createdAt(LocalDateTime.now())
                        .token(UUID.randomUUID() + "_" + user.getUsername())
                        .build();
                user.setSession(session);
                sessionRepository.save(session);
                log.info("New session is created {}", session);
                return session;
            } else {
                log.error("Password {} is wrong for user {}", password, user.getUsername());
                throw new CredentialsAreWrongException("Password is wrong");
            }
        } else {
            log.error("User with username {} is not found", username);
            throw new CredentialsAreWrongException("User is not found");
        }
    }

    @Override
    public boolean register(User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new UserIsExistsException("User with username is already exists: " + user.getUsername());
        } else {
            userRepository.save(user);
            return true;
        }
    }

    @Override
    public boolean logout(Long userId) {
        if (userRepository.findById(userId).isPresent()) {
            User user = userRepository.findById(userId).get();
            if (user.getSession() != null) {
                Session session = user.getSession();
                sessionRepository.delete(session);
                log.info("session for user {} is closed", user.getUsername());
                return true;
            } else {
                log.error("User {} does not have any sessions", userId);
                throw new SessionNotFoundException("Session is not found");

            }
        } else {
            log.error("userid {} is not found", userId);
            throw new UserNotFoundException("User is not found");
        }
    }

    @Override
    public List<Session> findAll() {
        return sessionRepository.findAll();
    }
}