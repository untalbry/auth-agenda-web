package com.binarybrains.external.jpa.repository;

import com.binarybrains.external.jpa.model.UserJpa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthenticationJpaRepository extends JpaRepository<UserJpa, Integer> {
}
