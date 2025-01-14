import {User} from './user.model';

export interface ChatMessageRequest {
  message: string;
}

export interface ChatMessageResponse {
  id: number;
  user: User;
  message: string;
  createdAt: string;
}
