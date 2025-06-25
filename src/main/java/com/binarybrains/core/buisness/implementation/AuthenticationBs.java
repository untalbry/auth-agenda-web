package com.binarybrains.core.buisness.implementation;

import com.binarybrains.core.buisness.input.AuthenticationService;
import com.binarybrains.core.buisness.output.AuthenticationRepository;
import com.binarybrains.core.entity.User;
import com.binarybrains.utils.ErrorCode;

import io.vavr.control.Either;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class AuthenticationBs implements AuthenticationService {

    private final AuthenticationRepository authenticationRepository;
    @Inject
    public AuthenticationBs(AuthenticationRepository authenticationRepository){
        this.authenticationRepository = authenticationRepository;
    }
    @Override
    public Either<ErrorCode, Boolean> register(User user) {
        Either<ErrorCode, Boolean> result;
        var existsUser = authenticationRepository.existUserByEmail(user.getEmail());
        if(existsUser){
            result = Either.left(ErrorCode.RN003);
        }else{
            result = authenticationRepository.create(user)
                    .map(signedInUser -> Either.<ErrorCode, Boolean>right(true))
                    .orElseGet(() -> Either.left(ErrorCode.RN001));
        }
        return result;
    }
    @Override
    public Either<ErrorCode, Boolean> login(User user) {
        Either<ErrorCode, Boolean> result;
        var existsUser = authenticationRepository.existUserByEmail(user.getEmail());
        if(!existsUser) {
            result = Either.left(ErrorCode.RN004);
        }else{
            var isPasswordCorrect = authenticationRepository.validatePassword(user.getEmail(), user.getPassword());
            if(!isPasswordCorrect){
                result = Either.left(ErrorCode.RN005);
            }else{
                result = Either.right(true);
            }
        }
        return result;
    }
    @Override
    public Either<ErrorCode, Boolean> resetPassword(String email) {

        return null;
    }
}
