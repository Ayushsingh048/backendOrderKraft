import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { AuthService } from '../auth.service'; // ✅ Adjust path if needed
import Swal from 'sweetalert2';

@Component({
  selector: 'app-login-page',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterModule],
  templateUrl: './login-page.html',
  styleUrls: ['./login-page.css']
})
export class LoginPage {
  loginForm: FormGroup;
  errorMessage = '';

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router
  ) {
    this.loginForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required]
    });
  }

  onSubmit() {
    if (this.loginForm.valid) {
      const credentials = this.loginForm.value;

      this.authService.login(credentials).subscribe({
        next: (response) => {
          console.log('Login Success:', response);

          // Save auth info to localStorage
          localStorage.setItem('authToken', response.token || 'dummy-token');
          localStorage.setItem('username', response.username);
          localStorage.setItem('authUser', JSON.stringify(response));

          // Correct role check: match with "Production Manager"
          const role = response.role?.trim();

          if (role === 'Production-manager') {
            this.router.navigate(['/production-manager']);
          } else {
            this.router.navigate(['/test']);
          }
        },
        error: (err) => {
          console.error('Login failed', err);
          const errorMsg =
            typeof err.error === 'string'
              ? err.error
              : err.error?.message || 'Login failed. Please try again.';

          if (errorMsg.toLowerCase().includes('locked')) {
            // Show popup ONLY for locked accounts
            Swal.fire({
              icon: 'error',
              title: 'Account Locked',
              text: errorMsg
            });
            this.errorMessage = '';
          }
          else {
            this.errorMessage = errorMsg;
          }
        }
      })
    }
  }
}
