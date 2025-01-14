package com.pro100user.com.chatapp.interactor;

import com.pro100user.com.chatapp.model.dto.request.ChatMessageRequest;
import com.pro100user.com.chatapp.model.dto.response.ChatMessageResponse;
import org.springframework.data.domain.Page;

public interface ChatInteractor {

    Page<ChatMessageResponse> findAll(int page, int size);

    ChatMessageResponse handleMessage(String username, ChatMessageRequest request);
}
