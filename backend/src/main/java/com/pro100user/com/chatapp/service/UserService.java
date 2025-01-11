package com.pro100user.com.chatapp.service;

import com.pro100user.com.chatapp.model.entity.UserEntity;

public interface UserService {

    UserEntity login(String username, String password);

    UserEntity createUser(UserEntity userEntity);
}
