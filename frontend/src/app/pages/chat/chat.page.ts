import { Component, OnInit, OnDestroy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { WebSocketService} from '../../services/websocket.service';
import {ChatMessage} from '../../models/chat-message.model';

@Component({
  selector: 'app-chat',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './chat.page.html',
  styleUrls: ['./chat.page.scss'],
})
export class ChatPage implements OnInit, OnDestroy {
  messages: ChatMessage[] = [];
  newMessage: string = '';

  constructor(private websocketService: WebSocketService) {
  }

  ngOnInit(): void {
    this.websocketService.getMessages().subscribe((message: ChatMessage) => {
      console.log('subscribe')
      this.messages.push(message);
    });
  }

  sendMessage(): void {
    if (!this.newMessage.trim()) {
      return;
    }

    const message: ChatMessage = {
      sender: 'You',
      message: this.newMessage,
      timestamp: new Date().toISOString(),
    };
    this.websocketService.sendMessage(message);
    this.newMessage = '';
  }

  ngOnDestroy(): void {
    this.websocketService.disconnect();
  }
}
