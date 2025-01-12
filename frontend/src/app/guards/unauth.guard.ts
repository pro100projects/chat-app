import { Injectable } from '@angular/core';
import { CanActivate, Router, UrlTree } from '@angular/router';

@Injectable({
  providedIn: 'root',
})
export class UnAuthGuard implements CanActivate {
  constructor(private router: Router) {}

  canActivate(): boolean | UrlTree {
    const isAuthenticated = !!localStorage.getItem('authToken');
    if (isAuthenticated) {
      return this.router.parseUrl('/chat');
    }
    return true;
  }
}
