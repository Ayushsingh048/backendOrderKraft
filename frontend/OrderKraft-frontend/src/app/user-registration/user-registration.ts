import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Router, ActivatedRoute } from '@angular/router';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-user-registration',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './user-registration.html',
  styleUrls: ['./user-registration.css']
})
export class UserRegistration implements OnInit {
  userForm!: FormGroup;
  roles: any[] = [];
  isSubmitted = false;
  emailExists = false;

  constructor(private fb: FormBuilder, private http: HttpClient,private router: Router, private route: ActivatedRoute) {}

  ngOnInit(): void {
    this.userForm = this.fb.group({
      username: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required],
      status: ['Active', Validators.required],
      roleName: ['', Validators.required]
    });

    this.fetchRoles();

    // Watch email input for changes to check for existence
    this.userForm.get('email')?.valueChanges.subscribe((email) => {
      this.checkEmailExists(email);
    });
  }

  fetchRoles(): void {
    this.http.get<any[]>('http://localhost:8081/roles/all').subscribe({
      next: (res) => {
        this.roles = res;
      },
      error: (err) => {
        console.error('Failed to fetch roles', err);
      }
    });
  }

  checkEmailExists(email: string): void {
    if (email && this.userForm.get('email')?.valid) {
      this.http
        .get<boolean>(`http://localhost:8081/users/check-email?email=${email}`)
        .subscribe({
          next: (exists: boolean) => {
            this.emailExists = exists;
            if (exists) {
              this.userForm.get('email')?.setErrors({ emailExists: true });
            } else {
              const errors = this.userForm.get('email')?.errors;
              if (errors) {
                delete errors['emailExists'];
                if (Object.keys(errors).length === 0) {
                  this.userForm.get('email')?.setErrors(null);
                } else {
                  this.userForm.get('email')?.setErrors(errors);
                }
              }
            }
          },
          error: (err) => {
            console.error('Email check failed', err);
          }
        });
    }
  }

  submitForm(): void {
    this.isSubmitted = true;

    if (this.userForm.invalid || this.emailExists) {
      Swal.fire({
        icon: 'warning',
        title: 'Validation Error',
        text: this.emailExists
          ? 'Email already exists. Please use a different email.'
          : 'Please fill all required fields correctly.'
      });
      return;
    }

    const userPayload = this.userForm.value;

    this.http.post('http://localhost:8081/users/add', userPayload).subscribe({
      next: () => {
        Swal.fire({
          icon: 'success',
          title: 'Success',
          text: 'User registered successfully'
        });
        this.userForm.reset();
        this.isSubmitted = false;
        this.emailExists = false;
        this.router.navigateByUrl(this.route.snapshot.queryParamMap.get('returnUrl') || '/admin');
      },
      error: (err) => {
        console.error('Registration failed', err);
        Swal.fire({
          icon: 'error',
          title: 'Error',
          text: 'Registration failed'
        });
      }
    });
  }
}
