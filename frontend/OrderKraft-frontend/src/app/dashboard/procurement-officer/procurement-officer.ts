import { Component, OnInit,ElementRef, HostListener, ViewChild} from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../auth.service'; 
import { ProfileSettings } from "../../pages/profile-settings/profile-settings";
import { View } from '../../pages/view/view';




@Component({
  selector: 'app-procurement-officer',
  standalone: true,
  imports: [CommonModule,FormsModule,ProfileSettings,View],
  templateUrl: './procurement-officer.html',
  styleUrl: './procurement-officer.css'
})
export class ProcurementOfficer implements OnInit {


 // Common
  //userId: number | null = null;
loggedInUser: any = {}; 
  username: string = '';
  email: string = '';
  roleName: string = '';
  userId: number = 0;
  activeTab: string = 'dashboard';

  // Data sources
  products: any[] = [];
  units: any[] = [];
  schedules: any[] = [];
  tasks: any[] = [];

  // Pagination state
  currentPages: { [key: string]: number } = {
    products: 1,
    units: 1,
    schedules: 1,
    tasks: 1
  };

  pageSize = 3;

  constructor(private http: HttpClient, private router: Router,private authService: AuthService) {}

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


 ngOnInit() {

    const storedUsername = this.authService.getEmail();
    if (storedUsername) {
      this.fetchUserDetails(storedUsername);
    } else {
      // Optional: redirect to login if username not found
      console.warn('Username not found in localStorage, redirecting to login.');
      this.router.navigate(['/login']);
    }
  
  }


  setActiveTab(tab: string): void {
    this.activeTab = tab;
  }

  // Pagination helpers
  paginate<T>(items: T[], tab: string): T[] {
    const start = (this.currentPages[tab] - 1) * this.pageSize;
    return items.slice(start, start + this.pageSize);
  }

  totalPages(items: any[]): number {
    return Math.ceil(items.length / this.pageSize);
  }

  nextPage(tab: string, items: any[]): void {
    if (this.currentPages[tab] < this.totalPages(items)) {
      this.currentPages[tab]++;
    }
  }

  prevPage(tab: string): void {
    if (this.currentPages[tab] > 1) {
      this.currentPages[tab]--;
    }
  }

  // Fetch user info
  fetchUserDetails(username: string): void {
    const url = `http://localhost:8081/users/search/email/${username}`;
    console.log("username:"+username);
    this.http.get<any>(url,{ withCredentials: true }).subscribe({
      next: (data) => {
        console.log(data);
        this.username = data.username;
        // this.email=data.email;
        this.roleName = data.role?.name || '';
        this.userId = data.id;

        this.loggedInUser = data;


        this.fetchProducts();
        this.fetchUnits();
        this.fetchSchedules();
        this.fetchTasks();
      },
      error: (err) => {
        console.error('Error fetching user:', err);
        this.router.navigate(['/login']);
      }
    });
  }

  // Fetch products
  fetchProducts(): void {
    const url = `http://localhost:8081/product/search/manager/${this.userId}`;
    this.http.get<any>(url).subscribe({
      next: (data) => this.products = Array.isArray(data) ? data : [data],
      error: (err) => console.error('Error fetching products:', err)
    });
  }

  // Fetch production units
  fetchUnits(): void {
    const url = `http://localhost:8081/production_unit/search/production_manager/${this.userId}`;
    this.http.get<any>(url).subscribe({
      next: (data) => this.units = Array.isArray(data) ? data : [data],
      error: (err) => console.error('Error fetching units:', err)
    });
  }

  // Fetch production schedules
  fetchSchedules(): void {
    const url = `http://localhost:8081/production_schedule/search/production_manager/${this.userId}`;
    this.http.get<any>(url).subscribe({
      next: (data) => this.schedules = Array.isArray(data) ? data : [data],
      error: (err) => console.error('Error fetching schedules:', err)
    });
  }

  // Fetch tasks
  fetchTasks(): void {
    const url = `http://localhost:8081/production_task/all`;
    this.http.get<any>(url).subscribe({
      next: (data) => {
        const managerId = this.userId;
        this.tasks = (Array.isArray(data) ? data : []).filter(
          task => task.productionSchedule?.user?.id === managerId
        );
      },
      error: (err) => console.error('Error fetching tasks:', err)
    });
  }

  // show profile 
  openProfileTab() {
  this.showDropdown = false; // close the dropdown
  this.activeTab = 'profile';
}
// show settings 
openSettingsTab() {
  this.showDropdown = false; // close the dropdown
  this.activeTab = 'settings';
}


  logout(): void {
   this.authService.logout();
  }

}
