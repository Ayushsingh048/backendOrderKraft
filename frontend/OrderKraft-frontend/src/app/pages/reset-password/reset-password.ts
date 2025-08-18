import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import Swal from 'sweetalert2';
import { AuthService } from '../../auth.service'; // ✅ Adjust path if needed

@Component({
  selector: 'app-reset-password',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './reset-password.html',
  styleUrls: ['./reset-password.css']
})
export class ResetPassword {
  resetForm: FormGroup;
  errorMessage = '';

  constructor(
    private fb: FormBuilder,
    private http: HttpClient,
    private router: Router,
    private authService: AuthService
  ) {
    this.resetForm = this.fb.group({
      oldPassword: ['', Validators.required],
      newPassword: [
        '',
        [
          Validators.required,
          Validators.minLength(8),
          Validators.pattern('^(?=.*[A-Z])(?=.*\\d).+$') // At least 1 uppercase and 1 number
        ]
      ],
      confirmPassword: ['', Validators.required]
    });
  }

  onSubmit(): void {
    this.errorMessage = '';

    if (this.resetForm.invalid) {
      this.errorMessage = 'Please fill all fields correctly.';
      return;
    }

    const { oldPassword, newPassword, confirmPassword } = this.resetForm.value;
     console.log(oldPassword);
     console.log(newPassword);
     console.log(confirmPassword);

    if (newPassword !== confirmPassword) {
      this.errorMessage = 'New password and confirm password do not match.';
      return;
    }

    const payload = {
      oldPassword,
      newPassword,
      confirmPassword
    };

    this.http.put('http://localhost:8081/users/reset-password', payload, { withCredentials: true,responseType: 'text' }).subscribe({
  next: () => {
    Swal.fire('Success', 'Password reset successfully', 'success');
    this.router.navigate(['/login']);
  },
  error: (err: any) => {
    console.log('Payload sent:', payload);
    console.error('Full Error:', err);
    this.errorMessage =
      err?.error && typeof err.error === 'string'
        ? err.error
        : 'Please enter valid details';
  }
});

  }
}
