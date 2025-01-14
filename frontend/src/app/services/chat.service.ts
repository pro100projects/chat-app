import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { HOST, API_ENDPOINTS } from '../constants';

@Injectable({
  providedIn: 'root',
})
export class ChatService {

  constructor(private http: HttpClient) {}

  getMessages(): Observable<any> {
    const authToken = localStorage.getItem('authToken');
    const headers = new HttpHeaders().set('Authorization', `Bearer ${authToken}`);
    return this.http.get(`${HOST}${API_ENDPOINTS.MESSAGES}`, { headers });
  }
}
