import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-user-registration',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, HttpClientModule],
  templateUrl: './user-registration.html',
  styleUrls: ['./user-registration.css']
})
export class UserRegistration implements OnInit {
  userForm!: FormGroup;
  roles: any[] = [];
  isSubmitted = false;

  constructor(private fb: FormBuilder, private http: HttpClient) {}

  ngOnInit(): void {
    this.userForm = this.fb.group({
      username: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required],
      status: ['Active', Validators.required],
      roleName: ['', Validators.required] // roleName must match backend DTO
    });

    this.fetchRoles();
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

  submitForm(): void {
    this.isSubmitted = true;

    if (this.userForm.invalid) {
      Swal.fire({
        icon: 'warning',
        title: 'Validation Error',
        text: 'Please fill all required fields correctly.'
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
