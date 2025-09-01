import { Component, OnInit,ElementRef, HostListener, ViewChild} from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../auth.service'; 


import { ProfileSettings } from '../../pages/profile-settings/profile-settings';
import { View } from '../../pages/view/view';
import { OrderCreation } from "../../pages/order-creation/order-creation";




@Component({
  selector: 'app-procurement-officer',
  standalone: true,
  imports: [CommonModule, FormsModule, ProfileSettings, View, OrderCreation],
  templateUrl: './procurement-officer.html',
  styleUrl: './procurement-officer.css'
})
export class ProcurementOfficer implements OnInit {


 // Common
  //userId: number | null = null;
  username: string = '';
  email: string = '';
  roleName: string = '';
  // userId: number = 0;
  orderId:number=0;


  userId:number=0;   //  store logged-in userId
  loggedInUser: any = {};         //  store user details
  activeTab: string = 'dashboard';

  // Data sources
  orders: any[] = [];
<<<<<<< HEAD
  units: any[] = [];
  schedules: any[] = [];
  tasks: any[] = [];

  // Pagination state
  currentPages: { [key: string]: number } = {
    orders: 1,
    units: 1,
    schedules: 1,
    tasks: 1
=======
  originalOrders: any[] = [];

  // Pagination state
  currentPages: { [key: string]: number } = {
  orders: 1,
    
>>>>>>> 5822be0a5fdb9bd4b345b71b57567dbd33655b97
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


        this.fetchOrders();
<<<<<<< HEAD
        this.fetchUnits();
        this.fetchSchedules();
        this.fetchTasks();
=======
        
>>>>>>> 5822be0a5fdb9bd4b345b71b57567dbd33655b97
      },
      error: (err) => {
        console.error('Error fetching user:', err);
        this.router.navigate(['/login']);
      }
    });
  }

  // Fetch orders
  fetchOrders(): void {
<<<<<<< HEAD
    const url = `http://localhost:8081/orders/all/${this.userId}`;
    this.http.get<any>(url).subscribe({
      next: (data) => {
        this.orders = data;
        console.log(data);
      },
      error: (err) => console.error('Error fetching orders:', err)
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
=======
  const url = `http://localhost:8081/orders/all`;
  this.http.get<any>(url).subscribe({
    next: (data) => {
      this.orders = Array.isArray(data) ? data : [data];
      this.originalOrders = [...this.orders];  // ✅ keep a copy for filtering
    },
    error: (err) => console.error('Error fetching orders:', err)
  });
}
  
>>>>>>> 5822be0a5fdb9bd4b345b71b57567dbd33655b97

  //Check Payment Status
  checkPaymentStatus(order: any) {
  order.paymentStatus = 'loading';

  this.http.get<{status: string}>(`http://localhost:8081/payments/status/${order.orderId}`)
    .subscribe({
      next: (res) => {
        console.log(status)

        if (status === 'succeeded') {
          order.paymentStatus = 'paid';
        } else if (status === 'initiated') {
          order.paymentStatus = 'pending';
        } else {
          order.paymentStatus = 'unavailable'; // fallback if unknown
        }
        order.showStatus = true;
      },
      error: () => {
        order.paymentStatus = 'unavailable';
        order.showStatus = true;
      }
    });
}

  // show profile 
  openProfileTab() {
  this.showDropdown = false; // close the dropdown
  this.activeTab = 'View Profile';
}
// show settings 
openSettingsTab() {
  this.showDropdown = false; // close the dropdown
  this.activeTab = 'settings';
}

//order creation
goToOrderCreation() {
  // this.authService.setUserId(this.userId);
  this.router.navigate(['/order-creation']);
}

  // user registration component display 
  showOrderCreationForm = false;

  toggleOrderCreationForm() {
    this.showOrderCreationForm = !this.showOrderCreationForm;
  }

  onOrderCreation() {
    this.showOrderCreationForm = false;
    this.fetchOrders(); // Refresh order list
  }

  // fetchOrders() {
  //   this.http.get<any[]>('http://localhost:8081//all').subscribe(data => {
  //     this.orders = data;
  //   });
  // }





  logout(): void {
   this.authService.logout();
  }



// Sorting 



  sortOrders(event: Event) {
  const selectElement = event.target as HTMLSelectElement;
  const sortBy = selectElement.value;

  if (sortBy === 'orderId') {
    this.orders.sort((a, b) => a.orderId - b.orderId);
  } else if (sortBy === 'orderDate') {
    this.orders.sort((a, b) => new Date(a.orderDate).getTime() - new Date(b.orderDate).getTime());
  }
}






filterOrders(event: Event): void {
  const selectElement = event.target as HTMLSelectElement;
  const status = selectElement.value.toLowerCase();

  if (!status) {
    this.orders = [...this.originalOrders]; // reset
  } else {
    this.orders = this.originalOrders.filter(order =>
      order.status?.toLowerCase() === status
    );
  }
}




}
