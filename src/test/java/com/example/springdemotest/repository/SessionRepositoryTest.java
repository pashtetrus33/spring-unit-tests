package com.example.springdemotest.repository;

import com.example.springdemotest.model.Session;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.Optional;


@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SessionRepositoryTest {

    @Autowired
    private SessionRepository sessionRepository;

    @Test
    @Order(1)
    @Rollback(value = false)
    public void saveSessionTest() {
        Session session = new Session();

        sessionRepository.save(session);

        Assertions.assertThat(session.getId()).isGreaterThan(0);
    }

    @Test
    @Order(2)
    @Rollback(value = false)
    public void getSessionTest() {
        Session session = sessionRepository.findById(1L).get();
        Assertions.assertThat(session.getId()).isEqualTo(1L);
    }

    @Test
    @Order(3)
    @Rollback(value = false)
    public void getListOfSessionsTest() {
        List<Session> sessions = sessionRepository.findAll();
        Assertions.assertThat(sessions.size()).isGreaterThan(0);
    }

    @Test
    @Order(4)
    @Rollback(value = false)
    public void updateSessionTest() {
        Session session = sessionRepository.findById(1L).get();
        session.setToken("token");
        Assertions.assertThat(session.getId()).isEqualTo(1L);
    }

    @Test
    @Order(5)
    @Rollback(value = false)
    public void deleteSessionTest() {
        Session session = sessionRepository.findById(1L).get();
        sessionRepository.delete(session);
        Session session1 = null;
        Optional<Session> optionalUser = sessionRepository.findById(1L);
        if (optionalUser.isPresent()) {
            session1 = optionalUser.get();
        }
        Assertions.assertThat(session1).isNull();
    }
}