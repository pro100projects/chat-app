package com.pro100user.com.chatapp.service.impl;

import com.pro100user.com.chatapp.model.entity.UserEntity;
import com.pro100user.com.chatapp.repository.UserRepository;
import com.pro100user.com.chatapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Optional<UserEntity> findByUsername(String email) {
        return userRepository.findByUsername(email);
    }

    @Override
    public UserEntity login(String username, String password) {
        var userEntity = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        if (!passwordEncoder.matches(password, userEntity.getPassword())) {
            throw new IllegalArgumentException("Wrong password");
        }
        return userEntity;
    }

    @Override
    public UserEntity createUser(UserEntity userEntity) {
        if (userRepository.existsByUsername(userEntity.getUsername())) {
            throw new IllegalArgumentException("This username is already in use");
        }
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        return userRepository.save(userEntity);
    }
}
