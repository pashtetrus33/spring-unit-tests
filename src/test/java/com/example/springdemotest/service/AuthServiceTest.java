package com.example.springdemotest.service;

import com.example.springdemotest.exceptions.CredentialsAreWrongException;
import com.example.springdemotest.exceptions.UserIsExistsException;
import com.example.springdemotest.exceptions.UserNotFoundException;
import com.example.springdemotest.model.Session;
import com.example.springdemotest.model.User;
import com.example.springdemotest.repository.SessionRepository;
import com.example.springdemotest.repository.UserRepository;
import com.example.springdemotest.service.impl.AuthServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

//@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private SessionRepository sessionRepository;

    @InjectMocks
    private AuthServiceImpl authService;

    private User user;


    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        //Arrange
        user = User.builder()
                .id(1L)
                .username("Dima")
                .password("12345")
                .age(22)
                .build();
    }

    @Test
    @Order(1)
    @Rollback(value = false)
    void registerPositive() {
        when(userRepository.save(Mockito.any(User.class))).thenReturn(user);
        //Act
        authService.register(user);

        //Assert
        Mockito.verify(userRepository, Mockito.times(1)).save(user);
        Assertions.assertThat(user.getId()).isGreaterThan(0);
        Assertions.assertThat(user).isNotNull();
    }

    @Test
    @Order(2)
    @Rollback(value = false)
    void registerNegative() {
        //Act
        userRepository.save(Mockito.any(User.class));

        //Assert
        given(authService.register(user)).willThrow(new UserIsExistsException("User with username is already exists: " + user.getUsername()));

    }

    @Test
    @Order(3)
    @Rollback(value = false)
    void loginPositive() {
        //Arrange
        when(userRepository.findByUsername(Mockito.any(String.class))).thenReturn(Optional.of(user));

        //Act
        Session session = authService.login(user.getUsername(), user.getPassword());

        //Assert
        Assertions.assertThat(session).isNotNull();
        Assertions.assertThat(session.getToken()).contains(user.getUsername());
    }

    @Test
    @Order(4)
    @Rollback(value = false)
    void loginNegative() {
        Assertions.assertThatThrownBy(() -> authService.login(user.getUsername(), user.getPassword())).isInstanceOf(CredentialsAreWrongException.class)
                .hasMessage("User is not found");
    }


    @Test
    @Order(5)
    @Rollback(value = false)
    void logout() {
        user.setSession(new Session());
        Long userId = user.getId();
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        Boolean result = authService.logout(userId);
        Assertions.assertThat(result).isTrue();
    }

    @Test
    @Order(6)
    @Rollback(value = false)
    void logoutNegative() {
        Assertions.assertThatThrownBy(() -> authService.logout(user.getId())).isInstanceOf(UserNotFoundException.class)
                .hasMessage("User is not found");
    }

    @Test
    @Order(7)
    @Rollback(value = false)
    void findAll() {
        //Arrange
        when(sessionRepository.findAll()).thenReturn(List.of(new Session()));

        //Act
        List<Session> sessionList = authService.findAll();

        //Assert
        Mockito.verify(sessionRepository, Mockito.times(1)).findAll();
        Assertions.assertThat(sessionList.size()).isGreaterThan(0);
    }
}