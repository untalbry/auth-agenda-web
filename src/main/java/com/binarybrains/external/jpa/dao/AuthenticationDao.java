package com.binarybrains.external.jpa.dao;

import com.binarybrains.core.buisness.output.AuthenticationRepository;
import com.binarybrains.core.entity.User;
import com.binarybrains.external.jpa.model.UserJpa;
import com.binarybrains.external.jpa.repository.AuthenticationJpaRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.Optional;

@ApplicationScoped
public class AuthenticationDao implements AuthenticationRepository {
    @PersistenceContext
    EntityManager entityManager;
    private static final String QUERY_EXISTS_USER_BY_EMAIL = "SELECT EXISTS(SELECT 1 FROM tac01_usuario WHERE tx_login = :email)";
    private final AuthenticationJpaRepository authenticationJpaRepository;

    @Inject
    public AuthenticationDao(AuthenticationJpaRepository authenticationJpaRepository){
        this.authenticationJpaRepository = authenticationJpaRepository;
    }


    @Override
    public boolean existUserByEmail(String email) {
        return (Boolean) entityManager
                .createNativeQuery(QUERY_EXISTS_USER_BY_EMAIL)
                .setParameter("email", email)
                .getSingleResult();
    }

    @Override
    public Optional<User> sigin(User user) {
        return Optional.of(authenticationJpaRepository.save(UserJpa.fromEntity(user)).toEntity());
    }


}
