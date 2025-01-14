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
  typingUsers: { [username: string]: any } = {};
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
    this.websocketService.getTypingUsernames().subscribe((username: string) => {
      this.addOrUpdateTypingUser(username);
    });
  }

  onTyping() {
    this.websocketService.sendTypingEvent();
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

  addOrUpdateTypingUser(username: string) {
    if (this.currentUser?.username == username) {
      return;
    }

    if (this.typingUsers[username]) {
      clearTimeout(this.typingUsers[username].timer);
    }

    this.typingUsers[username] = {
      timer: setTimeout(() => {
        this.removeTypingUser(username)
      }, 5000),
    };
  }

  removeTypingUser(username: string) {
    if (this.typingUsers[username]) {
      clearTimeout(this.typingUsers[username].timer);
      delete this.typingUsers[username];
    }
  }

  getTypingUsernames(): string[] {
    return Object.keys(this.typingUsers);
  }

  ngOnDestroy(): void {
    this.websocketService.disconnect();
  }

  formatTypingUsers(): string {
    const users = this.getTypingUsernames();
    if (users.length === 1) {
      return `${users[0]} is typing...`;
    } else {
      const lastUser = users.pop();
      return `${users.join(', ')} and ${lastUser} are typing...`;
    }
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
