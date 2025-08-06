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
          this.authService.saveToken(response.token);

          // Check for resetRequired
          if (response.resetRequired === false || response.resetRequired === 'false') {
            console.log('First-time login detected. Redirecting...');
  this.router.navigate(['/reset-password']);
  return;
  // console.log('Redirecting to reset-password because resetRequired is false');
  // this.router.navigate(['/reset-password']);
  // return; // ⛔ Stop further role-based routing
}


          const role = this.authService.getRole();

          if (role === 'PRODUCTION-MANAGER') {
            console.log("Production Manager is loaded");
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
            Swal.fire({
              icon: 'error',
              title: 'Account Locked',
              text: errorMsg
            });
            this.errorMessage = '';
          } else {
            this.errorMessage = errorMsg;
          }
        }
      });
    }
  }
}
