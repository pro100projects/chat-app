package com.pro100user.com.chatapp.interactor;

import com.pro100user.com.chatapp.model.dto.request.LoginRequest;
import com.pro100user.com.chatapp.model.dto.request.RegisterRequest;
import com.pro100user.com.chatapp.model.dto.response.TokenResponse;

public interface AuthInteractor {

    TokenResponse login(LoginRequest request);

    TokenResponse register(RegisterRequest request);
}
