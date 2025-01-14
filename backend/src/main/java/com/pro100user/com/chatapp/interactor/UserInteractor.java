package com.pro100user.com.chatapp.interactor;

import com.pro100user.com.chatapp.model.dto.response.UserResponse;

public interface UserInteractor {

    UserResponse getUserInfo(Long id);
}
