package com.pro100user.com.chatapp.mapper;

import com.pro100user.com.chatapp.model.dto.response.ChatMessageResponse;
import com.pro100user.com.chatapp.model.entity.ChatMessageEntity;
import org.mapstruct.Mapper;

@Mapper(uses = UserMapper.class)
public interface ChatMapper {

    ChatMessageResponse toChatMessageResponse(ChatMessageEntity chatMessageEntity);
}
