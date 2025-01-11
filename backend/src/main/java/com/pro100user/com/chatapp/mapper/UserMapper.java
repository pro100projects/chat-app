package com.pro100user.com.chatapp.mapper;

import com.pro100user.com.chatapp.model.dto.request.RegisterRequest;
import com.pro100user.com.chatapp.model.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {

    UserEntity registerRequestToUserEntity(RegisterRequest request);
}
