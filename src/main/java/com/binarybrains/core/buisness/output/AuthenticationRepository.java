package com.binarybrains.core.buisness.output;

import com.binarybrains.core.entity.User;

import java.util.Optional;

public interface AuthenticationRepository {
    boolean existUserByEmail(String email);
    Optional<User> create(User user);
}
