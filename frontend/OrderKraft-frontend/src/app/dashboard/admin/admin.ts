// admin.component.ts
import { Component, OnInit,ElementRef, HostListener, ViewChild  } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { AuthService } from '../../auth.service';
import { UserRegistration } from "../../user-registration/user-registration";


@Component({
  selector: 'app-admin',
  templateUrl: './admin.html',
  standalone: true,
  imports: [FormsModule, CommonModule, UserRegistration]
})
export class Admin implements OnInit {
  activeTab = 'dashboard';
  view = 'list';
  selectedUser: any = {};
  pageSize = 5;

  username: string = '';
  roleName:string = '';

  currentPages: Record<string, number> = {};

  products: any[] = [];
  units: any[] = [];
  schedules: any[] = [];
  tasks: any[] = [];
  users: any[] = [];
  roles: any[] = [];

// for the menu icon 
showMobileMenu: boolean = false;
showDropdown: boolean = false;
toggleDropdown() {
  this.showDropdown = !this.showDropdown;
}

// for the global click listener
 @ViewChild('profileButton') profileButton!: ElementRef;
  @ViewChild('dropdownMenu') dropdownMenu!: ElementRef;


  // for the global click listener
 @HostListener('document:click', ['$event'])
  handleClickOutside(event: Event) {
    const clickedInsideButton = this.profileButton?.nativeElement.contains(event.target);
    const clickedInsideDropdown = this.dropdownMenu?.nativeElement.contains(event.target);

    if (!clickedInsideButton && !clickedInsideDropdown) {
      this.showDropdown = false;
    }
  }


constructor(private http: HttpClient, private router: Router, private authService: AuthService) {}

  ngOnInit() {
const storedUsername = this.authService.getEmail();
    if (storedUsername) {
      this.fetchUserDetails(storedUsername);
    } else {
      // Optional: redirect to login if username not found
      console.warn('Username not found in localStorage, redirecting to login.');
      this.router.navigate(['/login']);
    }
    this.currentPages = {
      products: 1,
      units: 1,
      schedules: 1,
      tasks: 1,
      users: 1,
    };

    this.fetchProducts();
    this.fetchUnits();
    this.fetchSchedules();
    this.fetchTasks();
    this.fetchUsers();
    this.fetchRoles();
  }

// fetching the username and role 

  fetchUserDetails(email: string): void {
    const url = `http://localhost:8081/users/search/email/${email}`;
    this.http.get<any>(url,{ withCredentials: true }).subscribe({
      next: (data) => {console.log("data="+data+"url"+url);
        this.username = data.username;
        this.roleName = data.role?.name || '';
        
      },
      error: (err) => {
        console.error('Error fetching user:', err);
        this.router.navigate(['/login']);
      }
    });
  }

  setActiveTab(tab: string) {
    this.activeTab = tab;
  }

  paginate(data: any[], type: string): any[] {
    const start = ((this.currentPages?.[type] || 1) - 1) * this.pageSize;
    return data.slice(start, start + this.pageSize);
  }

  totalPages(data: any[]): number {
    return Math.ceil(data.length / this.pageSize);
  }

  prevPage(type: string) {
    if (this.currentPages[type] > 1) this.currentPages[type]--;
  }

  nextPage(type: string, data: any[]) {
    if (this.currentPages[type] < this.totalPages(data)) this.currentPages[type]++;
  }

  fetchProducts() {
    this.http.get<any[]>('http://localhost:8081/product/all').subscribe(data => {
      this.products = data;
    });
  }

  fetchUnits() {
    this.http.get<any[]>('http://localhost:8081/production_unit/all').subscribe(data => {
      this.units = data;
    });
  }

  fetchSchedules() {
    this.http.get<any[]>('http://localhost:8081/production_schedule/all').subscribe(data => {
      this.schedules = data;
    });
  }

  fetchTasks() {
    this.http.get<any[]>('http://localhost:8081/production_task/all').subscribe(data => {
      this.tasks = data;
    });
  }

  fetchUsers() {
    this.http.get<any[]>('http://localhost:8081/users/all').subscribe(data => {
      this.users = data;
    });
  }

  fetchRoles() {
  this.http.get<any[]>('http://localhost:8081/roles/all').subscribe({
    next: (data) => {
      this.roles = data;
    },
    error: (err) => console.error('Failed to fetch roles:', err)
  });
}


  editUser(user: any) {
  this.selectedUser = { ...user };  // user.id will be copied
  this.view = 'edit';
}

 cancelEdit() {
    this.selectedUser = {};
    this.view = 'list';
  }

 saveEditedUser() {
  const payload = {
    username: this.selectedUser.username,
    email: this.selectedUser.email,
    status: this.selectedUser.status,
    roleName: this.selectedUser.role.name
  };

  const url = `http://localhost:8081/users/update/admin/${this.selectedUser.id}`;
  this.http.put(url, payload).subscribe({
    next: () => {
      this.fetchUsers();
      this.cancelEdit();
    },
    error: (err) => console.error('Failed to update user:', err)
  });
}


// user registration component display 
 showRegistrationForm = false;

toggleRegistrationForm() {
  this.showRegistrationForm = !this.showRegistrationForm;
}

onUserRegistered() {
  this.showRegistrationForm = false;
  this.fetchUsers(); // Refresh user list
}
  logout(): void {
    localStorage.clear();
    this.router.navigate(['/login']);
  }
}
