package com.pro100user.com.chatapp.service;

import com.pro100user.com.chatapp.model.entity.UserEntity;

import java.util.Optional;

public interface UserService {

    Optional<UserEntity> findByUsername(String email);

    UserEntity login(String username, String password);

    UserEntity createUser(UserEntity userEntity);
}
