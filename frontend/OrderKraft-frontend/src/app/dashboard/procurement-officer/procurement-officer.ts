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
  originalOrders: any[] = [];

  // Pagination state
  currentPages: { [key: string]: number } = {
  orders: 1,
    
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
        
      },
      error: (err) => {
        console.error('Error fetching user:', err);
        this.router.navigate(['/login']);
      }
    });
  }

  // Fetch orders
  fetchOrders(): void {
  const url = `http://localhost:8081/orders/all`;
  this.http.get<any>(url).subscribe({
    next: (data) => {
      this.orders = Array.isArray(data) ? data : [data];
      this.originalOrders = [...this.orders];  // ✅ keep a copy for filtering
    },
    error: (err) => console.error('Error fetching orders:', err)
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



//   sortOrders(event: Event) {
//   const selectElement = event.target as HTMLSelectElement;
//   const sortBy = selectElement.value;

//   if (sortBy === 'orderId') {
//     this.orders.sort((a, b) => a.orderId - b.orderId);
//   } else if (sortBy === 'orderDate') {
//     this.orders.sort((a, b) => new Date(a.orderDate).getTime() - new Date(b.orderDate).getTime());
//   }
// }

// updated sort for sorting the date 

sortOrders(event: Event) {
  const selectElement = event.target as HTMLSelectElement;
  const sortBy = selectElement.value;

  if (sortBy === 'orderId') {
    this.orders.sort((a, b) => a.orderId - b.orderId);
  } 
  else if (sortBy === 'orderDate') {
    const parseDate = (dateStr: string): Date => {
      const [day, month, year] = dateStr.split('-').map(Number);
      return new Date(year, month - 1, day); // month is 0-based
    };

    this.orders.sort((a, b) => parseDate(a.orderDate).getTime() - parseDate(b.orderDate).getTime());
  }
}





// filterOrders(event: Event): void {
//   const selectElement = event.target as HTMLSelectElement;
//   const status = selectElement.value.toLowerCase();

//   if (!status) {
//     this.orders = [...this.originalOrders]; // reset
//   } else {
//     this.orders = this.originalOrders.filter(order =>
//       order.status?.toLowerCase() === status
//     );
//   }
// }


//updated filter code 

filterOrders(event: Event): void {
  const selectElement = event.target as HTMLSelectElement;
  let status = selectElement.value;

  if (!status) {
    this.orders = [...this.originalOrders];
  } else {
    // normalize underscores and spaces
    const normalize = (s: string) => s.replace(/[_\s]/g, '').toLowerCase();

    this.orders = this.originalOrders.filter(order =>
      normalize(order.status || '') === normalize(status)
    );
  }
}





}
