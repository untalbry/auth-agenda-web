package com.binarybrains.external.jpa.dao;

import com.binarybrains.core.buisness.output.AuthenticationRepository;
import com.binarybrains.core.entity.User;
import com.binarybrains.external.jpa.model.UserJpa;
import com.binarybrains.external.jpa.repository.AuthenticationJpaRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class AuthenticationDao implements AuthenticationRepository {
    @PersistenceContext
    EntityManager entityManager;
    private static final String QUERY_EXISTS_USER_BY_EMAIL = "SELECT id_usuario, tx_nombre, tx_primer_apellido, tx_segundo_apellido FROM tac01_usuario WHERE tx_login = :email";
    private static final String QUERY_IS_PASSWORD_CORRECT = "SELECT EXISTS(SELECT 1 FROM tac01_usuario WHERE tx_login =:email AND tx_password = :password)";
    private final AuthenticationJpaRepository authenticationJpaRepository;

    @Inject
    public AuthenticationDao(AuthenticationJpaRepository authenticationJpaRepository){
        this.authenticationJpaRepository = authenticationJpaRepository;
    }


    @Override
    @SuppressWarnings("unchecked")
    public Optional<List<User>> getUserByEmail(String email) {
        List<User> users = entityManager
                .createNativeQuery(QUERY_EXISTS_USER_BY_EMAIL)
                .setParameter("email", email)
                .getResultList()
                .stream()
                .map(row -> {
                    Object[] r = (Object[]) row;
                    return UserJpa.builder()
                            .id((Integer) r[0])
                            .name((String) r[1])
                            .lastName((String) r[2])
                            .secondLastName((String) r[3])
                            .build()
                            .toEntity();
                })
                .toList();
        return Optional.of(users);
    }
    @Override
    public boolean validatePassword(String email, String password) {
        return  (Boolean) entityManager.createNativeQuery(QUERY_IS_PASSWORD_CORRECT)
                .setParameter("email", email)
                .setParameter("password", password)
                .getSingleResult();
    }
    @Override
    public Optional<User> create(User user) {
        return Optional.of(authenticationJpaRepository.save(UserJpa.fromEntity(user)).toEntity());
    }


}
