import {Injectable} from '@angular/core';
import {Client, IMessage} from '@stomp/stompjs';
import SockJS from 'sockjs-client';
import {Observable, Subject} from 'rxjs';
import {ChatMessageRequest, ChatMessageResponse} from '../models/chat-message.model'

@Injectable({
  providedIn: 'root',
})
export class WebSocketService {
  private stompClient: Client;
  private messageSubject: Subject<ChatMessageResponse> = new Subject<ChatMessageResponse>();
  private typingUsernamesSubject: Subject<string> = new Subject<string>();

  private readonly messageUrl: string = 'http://localhost:8080/api/v1/chat';
  private readonly messagesTopic: string = '/chat/topic/messages';
  private readonly sendMessageEndpoint: string = '/chat/app/send';
  private readonly typingEventTopic: string = '/chat/topic/typing';
  private readonly sendTypingEventEndpoint: string = '/chat/app/typing';

  constructor() {
    const authToken = localStorage.getItem('authToken');
    const socket = new SockJS(`${this.messageUrl}?authorization=Bearer ${authToken}`);
    this.stompClient = new Client({
      webSocketFactory: () => socket,
      connectHeaders: {
        'is-web-socket': 'true'
      },
      reconnectDelay: 5000,
      onConnect: () => {
        console.log('Connected to WebSocket');

        this.stompClient.subscribe(this.messagesTopic, (message: IMessage) => {
          this.messageSubject.next(JSON.parse(message.body));
        });

        this.stompClient.subscribe(this.typingEventTopic, (message: IMessage) => {
          this.typingUsernamesSubject.next(JSON.parse(message.body));
        });
      },
      onWebSocketError: (error) => {
        console.error('WebSocket error:', error);
      },
    });

    this.stompClient.activate();
  }

  sendMessage(message: ChatMessageRequest): void {
    if (this.stompClient.connected) {
      this.stompClient.publish({
        destination: this.sendMessageEndpoint,
        body: JSON.stringify(message),
      });
    } else {
      console.error('STOMP client not connected');
    }
  }

  sendTypingEvent(): void {
    if (this.stompClient.connected) {
      this.stompClient.publish({
        destination: this.sendTypingEventEndpoint
      });
    } else {
      console.error('STOMP client not connected');
    }
  }

  getMessages(): Observable<ChatMessageResponse> {
    return this.messageSubject.asObservable();
  }

  getTypingUsernames(): Observable<string> {
    return this.typingUsernamesSubject.asObservable();
  }

  disconnect(): void {
    this.stompClient.deactivate();
  }
}
