import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-production-manager-page',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './production-manager.html'
})
export class ProductionManagerPage implements OnInit {
  // Common
  username: string = '';
  roleName: string = '';
  userId: number = 0;
  userApiUsername = 'manager_pm_123';
  activeTab: string = 'dashboard';
  showDropdown: boolean = false;

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
    tasks:1
  };

  pageSize = 3;

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.fetchUserDetails(this.userApiUsername);
  }

  toggleDropdown(): void {
    this.showDropdown = !this.showDropdown;
  }

  setActiveTab(tab: string): void {
    this.activeTab = tab;
  }

  // Generic pagination
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

  // Fetch user
  fetchUserDetails(username: string): void {
    const url = `http://localhost:9090/users/search/username/${username}`;
    this.http.get<any>(url).subscribe({
      next: (data) => {
        this.username = data.username;
        this.roleName = data.role?.name;
        this.userId = data.id;

        this.fetchProducts();
        this.fetchUnits();
        this.fetchSchedules();
        this.fetchTasks();
      },
      error: (err) => console.error('Error fetching user:', err)
    });
  }

  // Products
  fetchProducts(): void {
    const url = `http://localhost:9090/product/search/manager/${this.userId}`;
    this.http.get<any>(url).subscribe({
      next: (data) => this.products = Array.isArray(data) ? data : [data],
      error: (err) => console.error('Error fetching products:', err)
    });
  }

  // Units
  fetchUnits(): void {
    const url = `http://localhost:9090/production_unit/search/production_manager/${this.userId}`;
    this.http.get<any>(url).subscribe({
      next: (data) => this.units = Array.isArray(data) ? data : [data],
      error: (err) => console.error('Error fetching units:', err)
    });
  }

  // Schedules
  fetchSchedules(): void {
    const url = `http://localhost:9090/production_schedule/search/production_manager/${this.userId}`;
    this.http.get<any>(url).subscribe({
      next: (data) => this.schedules = Array.isArray(data) ? data : [data],
      error: (err) => console.error('Error fetching schedules:', err)
    });
  }

  // Tasks
  fetchTasks(): void {
  const url = `http://localhost:9090/production_task/all`;
  this.http.get<any>(url).subscribe({
    next: (data) => {
      const managerId = this.userId;
      // Filter tasks belonging to current manager
      this.tasks = (Array.isArray(data) ? data : []).filter(
        task => task.productionSchedule?.user?.id === managerId
      );
    },
    error: (err) => console.error('Error fetching tasks:', err)
  });
}



}


