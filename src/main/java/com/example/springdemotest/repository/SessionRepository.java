package com.example.springdemotest.repository;

import com.example.springdemotest.model.Session;
import org.springframework.data.jpa.repository.JpaRepository;


public interface SessionRepository extends JpaRepository<Session, Long> {
}
