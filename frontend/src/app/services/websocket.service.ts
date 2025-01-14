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

  private readonly messageUrl: string = 'http://localhost:8080/api/v1/chat';
  private readonly topic: string = '/chat/topic/messages';
  private readonly sendEndpoint: string = '/chat/app/send';

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
        this.stompClient.subscribe(this.topic, (message: IMessage) => {
          const chatMessage: ChatMessageResponse = JSON.parse(message.body);
          console.log('subscribe', message);
          this.messageSubject.next(chatMessage);
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
        destination: this.sendEndpoint,
        body: JSON.stringify(message),
      });
    } else {
      console.error('STOMP client not connected');
    }
  }

  getMessages(): Observable<ChatMessageResponse> {
    return this.messageSubject.asObservable();
  }

  disconnect(): void {
    this.stompClient.deactivate();
  }
}
