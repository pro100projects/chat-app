import {Component, OnDestroy, OnInit} from '@angular/core';
import {CommonModule} from '@angular/common';
import {FormsModule} from '@angular/forms';
import {WebSocketService} from '../../services/websocket.service';
import {ChatMessageRequest, ChatMessageResponse} from '../../models/chat-message.model';

@Component({
  selector: 'app-chat',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './chat.page.html',
  styleUrls: ['./chat.page.scss'],
})
export class ChatPage implements OnInit, OnDestroy {
  messages: ChatMessageResponse[] = [];
  newMessage: string = '';

  constructor(private websocketService: WebSocketService) {
  }

  ngOnInit(): void {
    this.websocketService.getMessages().subscribe((message: ChatMessageResponse) => {
      this.messages.push(message);
    });
  }

  sendMessage(): void {
    if (!this.newMessage.trim()) {
      return;
    }

    const message: ChatMessageRequest = {
      message: this.newMessage,
    };
    this.websocketService.sendMessage(message);
    this.newMessage = '';
  }

  ngOnDestroy(): void {
    this.websocketService.disconnect();
  }
}
