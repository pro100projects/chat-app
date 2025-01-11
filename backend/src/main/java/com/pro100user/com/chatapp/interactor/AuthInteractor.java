package com.pro100user.com.chatapp.interactor;

import com.pro100user.com.chatapp.model.dto.request.RegisterRequest;
import com.pro100user.com.chatapp.model.dto.response.TokenResponse;

public interface AuthInteractor {

    TokenResponse register(RegisterRequest request);
}
