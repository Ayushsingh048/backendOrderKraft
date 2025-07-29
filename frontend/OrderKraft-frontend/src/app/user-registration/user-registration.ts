import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { UserService } from '../services/user.service';
import { User } from '../models/user.model';
import { Role } from '../models/role.model';

@Component({
  selector: 'app-user-registration',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './user-registration.html',
  styleUrls: ['./user-registration.css'],
})
export class UserRegistration implements OnInit {
  user: User = {
    username: '',
    email: '',
    password: '',
    status: 'Active',
    roleName: ''
  };

  statusOptions: string[] = ['Active', 'Inactive'];
  roleOptions: Role[] = [];

  successMessage = '';
  errorMessage = '';

  constructor(private userService: UserService) {}

  ngOnInit(): void {
    this.loadRoles();
  }

  loadRoles(): void {
    this.userService.getRoles().subscribe({
      next: (roles) => {
        this.roleOptions = roles;
        if (roles.length > 0) {
          this.user.roleName = roles[0].name;
        }
      },
      error: (err: any) => {
        this.errorMessage = 'Failed to load roles.';
        console.error(err);
      }
    });
  }

  onSubmit(): void {
    this.userService.registerUser(this.user).subscribe({
      next: () => {
        this.successMessage = 'User registered successfully!';
        this.errorMessage = '';
        this.user = {
          username: '',
          email: '',
          password: '',
          status: 'Active',
          roleName: this.roleOptions[0]?.name || ''
        };
      },
      error: (error: any) => {
        console.error('Error adding user:', error);
        this.successMessage = '';
        this.errorMessage = 'Failed to register user.';
      }
    });
  }
}
