package com.pro100user.com.chatapp.interactor;

import com.pro100user.com.chatapp.model.dto.request.ChatMessageRequest;
import com.pro100user.com.chatapp.model.dto.response.ChatMessageResponse;

public interface ChatInteractor {

    ChatMessageResponse handleMessage(String username, ChatMessageRequest request);
}
