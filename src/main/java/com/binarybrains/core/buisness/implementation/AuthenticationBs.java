package com.binarybrains.core.buisness.implementation;

import com.binarybrains.core.buisness.input.AuthenticationService;
import com.binarybrains.core.buisness.output.AuthenticationRepository;
import com.binarybrains.core.entity.User;
import com.binarybrains.core.entity.UserAuth;
import com.binarybrains.utils.ErrorCode;

import io.smallrye.jwt.build.Jwt;
import io.vavr.control.Either;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.time.Instant;
import java.util.ArrayList;

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
        var existsUser = authenticationRepository.getUserByEmail(user.getEmail());
        if(existsUser.isEmpty()){
            result = Either.left(ErrorCode.RN003);
        }else{
            result = authenticationRepository.create(user)
                    .map(signedInUser -> Either.<ErrorCode, Boolean>right(true))
                    .orElseGet(() -> Either.left(ErrorCode.RN001));
        }
        return result;
    }
    @Override
    public Either<ErrorCode, UserAuth> login(User user) {
        Either<ErrorCode, UserAuth> result;
        var existsUser = authenticationRepository.getUserByEmail(user.getEmail());
        if(existsUser.isEmpty()) {
            result = Either.left(ErrorCode.RN004);
        }else{
            var isPasswordCorrect = authenticationRepository.validatePassword(user.getEmail(), user.getPassword());
            if(!isPasswordCorrect){
                result = Either.left(ErrorCode.RN005);
            }else{
                UserAuth userAuth = UserAuth.builder()
                        .id(existsUser.get().getFirst().getId())
                        .name(existsUser.get().getFirst().getName())
                        .lastName(existsUser.get().getFirst().getLastName())
                        .secondLastName(existsUser.get().getFirst().getSecondLastName())
                        .roles(new ArrayList<>())
                        .token(Jwt.audience("binarybrains@gmail.com")
                                .subject(user.getEmail())
                                .claim("roles", "user")
                                .expiresAt(Instant.now().plusSeconds(3600))
                                .sign())
                        .build();

                result = Either.right(userAuth);
            }
        }
        return result;
    }
    @Override
    public Either<ErrorCode, Boolean> resetPassword(String email) {

        return null;
    }
}
