import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { HOST, API_ENDPOINTS } from '../constants';


@Injectable({
  providedIn: 'root',
})
export class AuthService {

  constructor(private http: HttpClient) {}

  login(data: any): Observable<any> {
    return this.http.post(`${HOST}${API_ENDPOINTS.LOGIN}`, data);
  }

  register(data: any): Observable<any> {
    return this.http.post(`${HOST}${API_ENDPOINTS.REGISTER}`, data);
  }
}
