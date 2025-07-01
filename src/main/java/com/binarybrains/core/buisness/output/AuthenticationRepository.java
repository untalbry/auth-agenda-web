package com.binarybrains.core.buisness.output;

import com.binarybrains.core.entity.User;

import java.util.List;
import java.util.Optional;

public interface AuthenticationRepository {
    Optional<List<User>> getUserByEmail(String email);
    Optional<User> create(User user);
    boolean validatePassword(String email, String password);

}
