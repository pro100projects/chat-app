package com.pro100user.com.chatapp.controller;

import com.pro100user.com.chatapp.interactor.UserInteractor;
import com.pro100user.com.chatapp.model.dto.response.UserResponse;
import com.pro100user.com.chatapp.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserInteractor userInteractor;

    @GetMapping
    public UserResponse getUserInfo(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        return userInteractor.getUserInfo(userPrincipal.getId());
    }
}
