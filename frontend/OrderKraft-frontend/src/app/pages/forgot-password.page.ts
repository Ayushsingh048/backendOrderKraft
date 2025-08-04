import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-forgot-password',
  imports: [FormsModule,CommonModule],
  templateUrl: './forgot-password.page.html',
  styleUrl: './forgot-password.page.css'
})
export class ForgotPasswordPage {
  step = 1;
  email = '';
  otp = '';
  password = '';

  constructor(private http: HttpClient) {}

  sendOtp() {
    this.http.post('http://localhost:8081/api/auth/forgot-password', { email: this.email }, { responseType: 'text' }).subscribe({
      next: (response) => {
        console.log(response);
        alert('OTP sent to your email!');
        this.step = 2;
      },
      error: () => alert('Error sending OTP')
    });
  }

  verifyOtp() {
    this.http.post('http://localhost:8081/api/auth/verify-otp', { email: this.email, otp: this.otp }, { responseType: 'text' }).subscribe({
      next: (response) => {
        alert('OTP verified!');
        this.step = 3;
      },
      error: () => alert('Invalid OTP')
    });
  }

  onSubmit() {
    this.http.post('http://localhost:8081/api/auth/reset-password', { email: this.email, password: this.password }, { responseType: 'text' }).subscribe({
      next: (response) => {
        alert('Password reset successfully');
        // Optional: redirect to login
      },
      error: () => alert('Failed to reset password')
    });
  }
}
