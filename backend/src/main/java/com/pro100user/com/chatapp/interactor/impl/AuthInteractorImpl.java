package com.pro100user.com.chatapp.interactor.impl;

import com.pro100user.com.chatapp.interactor.AuthInteractor;
import com.pro100user.com.chatapp.mapper.UserMapper;
import com.pro100user.com.chatapp.model.dto.request.LoginRequest;
import com.pro100user.com.chatapp.model.dto.request.RegisterRequest;
import com.pro100user.com.chatapp.model.dto.response.TokenResponse;
import com.pro100user.com.chatapp.security.JwtProvider;
import com.pro100user.com.chatapp.security.UserPrincipal;
import com.pro100user.com.chatapp.service.ChatService;
import com.pro100user.com.chatapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthInteractorImpl implements AuthInteractor {

    private final UserMapper userMapper;
    private final UserService userService;
    private final ChatService chatService;
    private final JwtProvider jwtProvider;

    @Override
    public TokenResponse login(LoginRequest request) {
        var userEntity = userService.login(request.username(), request.password());
        var userPrincipal = UserPrincipal.generateUserPrincipal(userEntity);
        return jwtProvider.generateTokens(userPrincipal);
    }

    @Override
    public TokenResponse register(RegisterRequest request) {
        if (!request.password().equals(request.repeatPassword())) {
            throw new IllegalArgumentException("Passwords do not match");
        }
        var userEntity = userMapper.toUserEntity(request);
        userEntity = userService.createUser(userEntity);
        chatService.createJoiningToChatMessage(userEntity);
        var userPrincipal = UserPrincipal.generateUserPrincipal(userEntity);
        return jwtProvider.generateTokens(userPrincipal);
    }
}
