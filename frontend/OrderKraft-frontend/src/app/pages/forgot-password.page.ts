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
  step = 2;
  email = '';
  otp = '';
  password = '';

  constructor(private http: HttpClient) {}

  sendOtp() {
    this.http.post('/api/send-otp', { email: this.email }).subscribe({
      next: () => {
        alert('OTP sent to your email!');
        this.step = 2;
      },
      error: () => alert('Error sending OTP')
    });
  }

  verifyOtp() {
    this.http.post('/api/verify-otp', { email: this.email, otp: this.otp }).subscribe({
      next: () => {
        alert('OTP verified!');
        this.step = 3;
      },
      error: () => alert('Invalid OTP')
    });
  }

  onSubmit() {
    this.http.post('/api/reset-password', { email: this.email, password: this.password }).subscribe({
      next: () => {
        alert('Password reset successfully');
        // Optional: redirect to login
      },
      error: () => alert('Failed to reset password')
    });
  }
}
