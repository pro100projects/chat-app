package com.pro100user.com.chatapp.controller;

import com.pro100user.com.chatapp.interactor.AuthInteractor;
import com.pro100user.com.chatapp.model.dto.request.LoginRequest;
import com.pro100user.com.chatapp.model.dto.request.RegisterRequest;
import com.pro100user.com.chatapp.model.dto.response.TokenResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthInteractor authInteractor;

    @PostMapping("/login")
    public TokenResponse login(@RequestBody @Valid LoginRequest request) {
        return authInteractor.login(request);
    }

    @PostMapping("/register")
    public TokenResponse register(@RequestBody @Valid RegisterRequest request) {
        return authInteractor.register(request);
    }
}
