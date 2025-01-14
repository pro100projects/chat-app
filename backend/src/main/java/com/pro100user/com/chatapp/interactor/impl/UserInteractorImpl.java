package com.pro100user.com.chatapp.interactor.impl;

import com.pro100user.com.chatapp.interactor.UserInteractor;
import com.pro100user.com.chatapp.mapper.UserMapper;
import com.pro100user.com.chatapp.model.dto.response.UserResponse;
import com.pro100user.com.chatapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserInteractorImpl implements UserInteractor {

    private final UserMapper userMapper;
    private final UserService userService;

    @Override
    public UserResponse getUserInfo(Long id) {
        var userEntity = userService.findById(id).orElseThrow();
        return userMapper.toUserResponse(userEntity);
    }
}
