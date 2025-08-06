import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Component, ElementRef, QueryList, ViewChildren,  } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-forgot-password',
  imports: [FormsModule,CommonModule, RouterModule],
  templateUrl: './forgot-password.page.html',
  styleUrl: './forgot-password.page.css'
})
export class ForgotPasswordPage {
  step = 1;
  email = '';
  otp: string[] = new Array(6).fill('');
  otpDigits = new Array(6);
  password = '';

  constructor(private http: HttpClient, private router: Router) {}
sendOtp() {
  Swal.fire({
    title: 'Sending OTP...',
    text: 'Please wait while we send the OTP to your email.',
    allowOutsideClick: false,
    allowEscapeKey: false,
    didOpen: () => {
      Swal.showLoading();
    }
  });

  this.http.post('http://localhost:8081/api/auth/forgot-password', { email: this.email }, { responseType: 'text' }).subscribe({
    next: (response) => {
      setTimeout(() => {
        Swal.close();
        Swal.fire({
          icon: 'success',
          title: 'OTP Sent!',
          text: 'OTP has been sent to your email.',
        });
        this.step = 2;
      }, 500);
    },
    error: (err) => {
      Swal.close();
      if (err.status === 404) {
      Swal.fire({
        icon: 'warning',
        title: 'User Not Found',
        text: 'No account registered with this email.',
      });
    } else {
      Swal.fire({
        icon: 'error',
        title: 'Error',
        text: err.error || 'Something went wrong while sending the OTP.',
      });
    }
    }
  });
}


  verifyOtp() {
    const finalOtp = this.otp.join('');
    this.http.post('http://localhost:8081/api/auth/verify-otp', { email: this.email, otp: finalOtp }, { responseType: 'text' }).subscribe({
      next: (response) => {
        Swal.fire({
          icon: 'success',
          title: 'OTP Verified',
          text: 'You can now reset your password.',
          showConfirmButton: false,
          timer: 2000,
          timerProgressBar: true,
          position: 'center'
        });

      },
      error: () => {
        Swal.fire({
          icon: 'error',
          title: 'Invalid OTP',
          text: 'Please enter the correct OTP.',
          showConfirmButton: true,
          confirmButtonColor: '#6366F1', // Indigo
          position: 'center'
        });
      }
    });
  }


  onSubmit() {
    this.http.post('http://localhost:8081/api/auth/reset-password', { email: this.email, password: this.password }, { responseType: 'text' }).subscribe({
      next: (response) => {
        Swal.fire({
          icon: 'success',
          title: 'Password Reset Successful',
          text: 'Your password has been updated.',
          confirmButtonColor: '#6366F1', // Indigo-500
          position: 'center'
        }).then(() => {
          // Redirect to login page
          this.router.navigate(['/login']);
});

      },
      error: () => {
        Swal.fire({
          icon: 'error',
          title: 'Reset Failed',
          text: 'Something went wrong while resetting your password.',
          confirmButtonColor: '#EF4444', // Red-500
          position: 'center'
        }).then(() => {
          // Still redirect to login
          this.router.navigate(['/login']);
        });
      }
    });
  }

  // ✅ OTP Input Handling Methods

@ViewChildren('otpInput') otpInputs!: QueryList<ElementRef>;

onInput(event: any, index: number) {
  const input = event.target;
  const value = input.value;

  // Only accept numbers and auto-move
  if (/^[0-9]$/.test(value)) {
    this.otp[index] = value;

    if (index < this.otpInputs.length - 1) {
      const nextInput = this.otpInputs.toArray()[index + 1];
      nextInput.nativeElement.focus();
    }
  } else {
    // Clear invalid input
    this.otp[index] = '';
    input.value = '';
  }
}

onKeyDown(event: KeyboardEvent, index: number) {
  if (!/^[0-9]$/.test(event.key) &&
      event.key !== 'Backspace' &&
      event.key !== 'Delete' &&
      event.key !== 'Tab') {
    event.preventDefault();
  }

  if ((event.key === 'Backspace' || event.key === 'Delete') && index > 0) {
    this.otp[index] = '';
    const inputs = document.querySelectorAll<HTMLInputElement>('input[name^="otp"]');
    inputs[index - 1]?.focus();
  }
}

onFocus(event: FocusEvent) {
  (event.target as HTMLInputElement).select();
}

onPaste(event: ClipboardEvent) {
  event.preventDefault();
  const text = event.clipboardData?.getData('text') ?? '';
  if (/^[0-9]{6}$/.test(text)) {
    this.otp = text.split('');
    const inputs = document.querySelectorAll<HTMLInputElement>('input[name^="otp"]');
    inputs[5]?.focus(); // focus last
  }
}

newPassword: string = '';
confirmPassword: string = '';
passwordMismatch: boolean = false;
confirmPasswordTouched = false;

checkPasswordMatch(): void {
  if (this.confirmPasswordTouched) {
    this.passwordMismatch = this.newPassword !== this.confirmPassword;
  }
}
onConfirmPasswordInput(): void {
  this.confirmPasswordTouched = true;
  this.checkPasswordMatch();
}
showNewPassword: boolean = false;

toggleNewPassword() {
  this.showNewPassword = !this.showNewPassword;
}

showConfirmPassword: boolean = false;

toggleConfirmPassword() {
  this.showConfirmPassword = !this.showConfirmPassword;
}


}
