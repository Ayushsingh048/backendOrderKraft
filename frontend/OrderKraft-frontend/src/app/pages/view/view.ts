import { Component, Input,OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-view',
  templateUrl: './view.html',
 imports: [CommonModule,FormsModule],
})
export class View implements OnInit {

 @Input() userId: number | null = null;       // passed from parent
  @Input() loggedInUser: any;        // passed from parent (username, email, etc.)
  

  activeTab: string = 'profile';
  showDropdown: boolean = false;

  errorMessage: string = '';

  constructor(private http: HttpClient) {}

  ngOnInit(): void {}

  // Open Profile
  showProfile() {
    this.activeTab = 'profile';
    this.showDropdown = false;
  }

  setActiveTab(tab: string) {
    this.activeTab = tab;
  }
}
