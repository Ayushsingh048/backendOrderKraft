import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { AuthService } from '../auth.service';
import Swal from 'sweetalert2';
import { ProcurementOfficer } from '../dashboard/procurement-officer/procurement-officer';

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
  showPassword = false;

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

  togglePassword() {
    this.showPassword = !this.showPassword;
  }

  onSubmit() {
    if (!this.loginForm.valid) {
      return;
    }

    const credentials = this.loginForm.value;

    this.authService.login(credentials).subscribe({
      next: (response) => {
        console.log('Login Success:', response);

        // First check if API directly provides resetRequired flag
        if (response?.hasOwnProperty('resetRequired')) {
          if (response.resetRequired === false) {
            console.log('First-time login → redirecting to reset password');
            this.router.navigate(['/reset-password']);
            return;
          }
        }

        // Otherwise, fetch user info for resetRequired and role
        this.authService.fetchUserInfo().subscribe({
          next: (userInfo) => {
            console.log('Fetched user info:', userInfo);

            if (userInfo?.resetRequired === false) {
              console.log('First-time login from userInfo → redirecting to reset password');
              this.router.navigate(['/reset-password']);
              return;
            }

            const role = userInfo?.role?.toUpperCase();
            console.log('User role:', role);

            switch (role) {
              case 'PRODUCTION MANAGER':
                this.router.navigate(['/production-manager']);
                break;
              case 'ADMIN':
                this.router.navigate(['/admin']);
                break;
              case 'PROCUREMENT OFFICER' :
                this.router.navigate(['/procurement-officer']);
                break;
              default:
                this.router.navigate(['/test']);
                break;
            }
          },
          error: (err) => {
            console.error('Error fetching user info', err);
            this.router.navigate(['/test']); // fallback route
          }
        });
      },
      error: (err) => {
        console.error('Login failed', err);
        const errorMsg =
          typeof err.error === 'string'
            ? err.error
            : err.error?.message || 'Login failed. Please try again.';

        const lowerMsg = errorMsg.toLowerCase();

        if (lowerMsg.includes('account locked')) {
          Swal.fire({
            icon: 'error',
            title: 'Account Locked',
            text: errorMsg,
            confirmButtonText: 'OK',
            customClass: {
              confirmButton: 'bg-red-600 text-white px-4 py-2 rounded hover:bg-red-700'
            }
          });
        } else if (lowerMsg.includes('invalid password')) {
          Swal.fire({
            icon: 'warning',
            title: 'Invalid Credentials',
            text: errorMsg,
            confirmButtonText: 'Try Again',
            customClass: {
              confirmButton: 'bg-yellow-500 text-white px-4 py-2 rounded hover:bg-yellow-600'
            }
          });
        } else if (lowerMsg.includes('invalid credentials')) {
          Swal.fire({
            icon: 'error',
            title: 'Login Failed',
            text: 'No user found with this email address.',
            confirmButtonText: 'Retry',
            customClass: {
              confirmButton: 'bg-indigo-600 text-white px-4 py-2 rounded hover:bg-indigo-700'
            }
          });
        } else {
          Swal.fire({
            icon: 'error',
            title: 'Login Error',
            text: errorMsg
          });
        }
      }
    });
  }
}
