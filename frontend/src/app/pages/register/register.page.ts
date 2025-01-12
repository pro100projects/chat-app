import {Component} from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {CommonModule} from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import {AuthService} from '../../services/auth.service';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, HttpClientModule],
  templateUrl: './register.page.html',
  styleUrl: './register.page.scss'
})
export class RegisterPage {
  registerForm: FormGroup;
  passwordMismatchError: boolean = false;
  errorMessage: string | null = null;

  constructor(private formBuilder: FormBuilder, private authService: AuthService) {
    this.registerForm = this.formBuilder.group({
      name: ['', [Validators.required]],
      surname: ['', [Validators.required]],
      username: ['', [Validators.required]],
      password: ['', [Validators.required, Validators.minLength(8)]],
      repeatPassword: ['', [Validators.required, Validators.minLength(8)]],
    });
  }

  onSubmit(): void {
    if (this.registerForm.invalid) {
      return;
    }

    const formData = this.registerForm.value;
    if (formData.password !== formData.repeatPassword) {
      this.passwordMismatchError = true;
      return;
    } else {
      this.passwordMismatchError = false;
    }

    this.authService.register(formData).subscribe({
      next: (response) => {
        localStorage.setItem('authToken', response.authToken);
        localStorage.setItem('refreshToken', response.refreshToken);
        alert('Registration successful!');
      },
      error: (error) => {
        this.handleError(error);
      },
    });
  }

  private handleError(error: any): void {
    this.errorMessage = error?.message || 'Registration failed. Please try again.';
  }
}
