package com.binarybrains.core.buisness.input;


import com.binarybrains.core.entity.User;
import com.binarybrains.core.entity.UserAuth;
import com.binarybrains.utils.ErrorCode;
import io.vavr.control.Either;

public interface AuthenticationService {
    Either<ErrorCode, Boolean>register(User user);
    Either<ErrorCode, UserAuth> login(User user);
    Either<ErrorCode, Boolean> resetPassword(String email);

}
