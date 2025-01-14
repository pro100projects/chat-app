import {CommonModule} from '@angular/common';
import {Component, OnDestroy} from '@angular/core';
import {RouterModule} from '@angular/router';
import {Subscription} from 'rxjs';
import {AuthService} from '../../services/auth.service';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './header.component.html',
  styleUrl: './header.component.scss'
})
export class HeaderComponent implements OnDestroy {
  isAuthenticated: boolean = false;
  private authSubscription: Subscription;

  constructor(private authService: AuthService) {
    this.authSubscription = this.authService.isAuthenticated$.subscribe(status => {
      this.isAuthenticated = status;
    });
  }

  logout(): void {
    this.authService.logout();
  }

  ngOnDestroy(): void {
    if (this.authSubscription) {
      this.authSubscription.unsubscribe();
    }
  }
}
