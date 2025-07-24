import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../auth.service'; // ✅ Adjust path if needed

@Component({
  selector: 'app-login-page',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
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
          this.authService.saveToken(response.token); // 👈 Adjust key as per your backend
          this.router.navigate(['/test']); // 👈 Replace with your target route
        },
        error: (err) => {
          console.error('Login failed', err);
          this.errorMessage = 'Invalid email or password';
          // this.router.navigate(['/test']);
        }
      });
    } else {
      this.errorMessage = 'Please enter valid credentials.';
    }
  }
}
