import { CommonModule } from '@angular/common';
import { Component, inject } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
// import { Router } from 'express';

@Component({
  selector: 'app-unauthorized',
  imports: [CommonModule,RouterModule],
  standalone: true,
  templateUrl: './unauthorized.html',
  styleUrl: './unauthorized.css'
})
export class Unauthorized {
  private router = inject(Router);
  
goToLogin(){
  this.router.navigate(['/']); 
}
}
