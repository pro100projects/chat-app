package com.pro100user.com.chatapp.mapper;

import com.pro100user.com.chatapp.model.dto.request.RegisterRequest;
import com.pro100user.com.chatapp.model.dto.response.UserResponse;
import com.pro100user.com.chatapp.model.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    UserEntity toUserEntity(RegisterRequest request);

    UserResponse toUserResponse(UserEntity userEntity);
}
