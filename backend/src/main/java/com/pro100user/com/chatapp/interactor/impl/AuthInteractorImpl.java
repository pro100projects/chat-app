package com.pro100user.com.chatapp.interactor.impl;

import com.pro100user.com.chatapp.interactor.AuthInteractor;
import com.pro100user.com.chatapp.mapper.UserMapper;
import com.pro100user.com.chatapp.model.dto.request.RegisterRequest;
import com.pro100user.com.chatapp.model.dto.response.TokenResponse;
import com.pro100user.com.chatapp.security.JwtProvider;
import com.pro100user.com.chatapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthInteractorImpl implements AuthInteractor {

    private final UserMapper userMapper;
    private final UserService userService;
    private final JwtProvider jwtProvider;

    @Override
    public TokenResponse register(RegisterRequest request) {
        if (!request.password().equals(request.repeatPassword())) {
            throw new IllegalArgumentException("Passwords do not match");
        }
        var userEntity = userMapper.registerRequestToUserEntity(request);
        userEntity = userService.createUser(userEntity);
        return jwtProvider.generateTokens(userEntity);
    }
}
