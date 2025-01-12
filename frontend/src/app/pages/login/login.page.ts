import {Component} from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {CommonModule} from '@angular/common';
import {Router} from '@angular/router';
import {HttpClientModule} from '@angular/common/http';
import {AuthService} from '../../services/auth.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, HttpClientModule],
  templateUrl: './login.page.html',
  styleUrls: ['./login.page.scss'],
})
export class LoginPage {
  loginForm: FormGroup;
  errorMessage: string | null = null;

  constructor(
    private router: Router,
    private formBuilder: FormBuilder,
    private authService: AuthService) {
    this.loginForm = this.formBuilder.group({
      username: ['', [Validators.required]],
      password: ['', [Validators.required]],
    });
  }

  onSubmit(): void {
    if (this.loginForm.invalid) {
      return;
    }
    this.errorMessage = null;

    const formData = this.loginForm.value;

    this.authService.login(formData).subscribe({
      next: (response) => {
        localStorage.setItem('authToken', response.authToken);
        localStorage.setItem('refreshToken', response.refreshToken);
        alert('Login successful!');
        this.router.navigate(['/chat']);
      },
      error: (error) => {
        this.handleError(error);
      },
    });
  }

  private handleError(error: any): void {
    this.errorMessage = error?.message || 'Login failed. Please try again.';
  }
}
