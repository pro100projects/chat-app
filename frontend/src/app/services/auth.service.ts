import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {BehaviorSubject, Observable, tap} from 'rxjs';
import {API_ENDPOINTS, HOST} from '../constants';
import {Router} from '@angular/router';


@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private isAuthenticatedSubject = new BehaviorSubject<boolean>(this.checkAuthToken());
  isAuthenticated$ = this.isAuthenticatedSubject.asObservable();

  constructor(
    private router: Router,
    private http: HttpClient
  ) {
  }

  private checkAuthToken(): boolean {
    return !!localStorage.getItem('authToken');
  }

  login(data: any): Observable<any> {
    return this.http.post(`${HOST}${API_ENDPOINTS.LOGIN}`, data).pipe(
      tap(() => this.isAuthenticatedSubject.next(true)));
  }

  register(data: any): Observable<any> {
    return this.http.post(`${HOST}${API_ENDPOINTS.REGISTER}`, data);
  }

  logout(): void {
    localStorage.removeItem('authToken');
    this.isAuthenticatedSubject.next(false);
    this.router.navigate(['/login']);
  }
}
