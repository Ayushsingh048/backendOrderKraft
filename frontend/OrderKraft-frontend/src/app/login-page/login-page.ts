import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { AuthService } from '../auth.service';

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
              } else {
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
          this.errorMessage = 'Invalid email or password';
        }
      });
    } else {
      this.errorMessage = 'Please enter valid credentials.';
    }
  }
}
