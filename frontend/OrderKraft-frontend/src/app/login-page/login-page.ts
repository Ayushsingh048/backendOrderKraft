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

          // ✅ Fetch user info after login, then handle role-based navigation
          this.authService.fetchUserInfo().subscribe({
            next: (userInfo) => {
              const role = userInfo.role?.toUpperCase(); // ensure consistency
              console.log("Fetched role from backend:", role);

              // ✅ Navigate based on role
              if (role === 'PRODUCTION-MANAGER') {
                this.router.navigate(['/production-manager']);
              } else if(role==='ADMIN'){
                this.router.navigate(['/admin']);
              }else{
                this.router.navigate(['/test']);
              }
            },
            error: (err) => {
              console.error("Error fetching user info", err);
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
            this.errorMessage = '';
          }

          else if (lowerMsg.includes('invalid password')) {
            Swal.fire({
              icon: 'warning',
              title: 'Invalid Credentials',
              text: errorMsg, // e.g., "Invalid password. Attempt 2 of 5."
              confirmButtonText: 'Try Again',
              customClass: {
                confirmButton: 'bg-yellow-500 text-white px-4 py-2 rounded hover:bg-yellow-600'
              }
            });
            this.errorMessage = '';
          }

          else if (lowerMsg.includes('invalid credentials')) {
            Swal.fire({
              icon: 'error',
              title: 'Login Failed',
              text: 'No user found with this email address.',
              confirmButtonText: 'Retry',
              customClass: {
                confirmButton: 'bg-indigo-600 text-white px-4 py-2 rounded hover:bg-indigo-700'
              }
            });
            this.errorMessage = '';
          }

          else {
            Swal.fire({
              icon: 'error',
              title: 'Login Error',
              text: errorMsg,
            });
            this.errorMessage = '';
          }
        }

      })
    }
  }

  showPassword: boolean = false;

togglePassword() {
  this.showPassword = !this.showPassword;
}

}
