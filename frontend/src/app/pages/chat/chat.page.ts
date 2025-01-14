import {Component, OnDestroy, OnInit} from '@angular/core';
import {CommonModule} from '@angular/common';
import {FormsModule} from '@angular/forms';
import {WebSocketService} from '../../services/websocket.service';
import {ChatMessageDto, ChatMessageRequest, ChatMessageResponse} from '../../models/chat-message.model';
import {ChatService} from '../../services/chat.service';
import {User} from '../../models/user.model';
import {UserService} from '../../services/user.service';

@Component({
  selector: 'app-chat',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './chat.page.html',
  styleUrls: ['./chat.page.scss'],
})
export class ChatPage implements OnInit, OnDestroy {
  currentUser: User | undefined;
  messages: ChatMessageDto[] = [];
  newMessage: string = '';

  constructor(
    private userService: UserService,
    private chatService: ChatService,
    private websocketService: WebSocketService) {
  }

  ngOnInit(): void {
    this.userService.getUserInfo().subscribe({
      next: (response: User) => {
        this.currentUser = response;
      },
      error: (error) => {
        console.error('Error during getting user info', error);
        alert('Error during getting user info');
      },
    });

    this.chatService.getMessages().subscribe({
      next: ({content}) => {
        this.messages = content.map((message: ChatMessageResponse) => ({
          ...message,
          createdAt: this.convertToDate(message.createdAt),
        }));
      },
      error: (error) => {
        console.error('error', error);
        alert("error during getting chat messages");
      },
    });
    this.websocketService.getMessages().subscribe((message: ChatMessageResponse) => {
      this.messages.push({...message, createdAt: this.convertToDate(message.createdAt)});
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

  private convertToDate(dateArray: number[]): Date {
    return new Date(
      dateArray[0],
      dateArray[1] - 1,
      dateArray[2],
      dateArray[3],
      dateArray[4],
      dateArray[5],
      dateArray[6] / 1000000
    );
  }
}
