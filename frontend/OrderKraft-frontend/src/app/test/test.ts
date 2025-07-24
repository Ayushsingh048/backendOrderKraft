import { Component, inject } from '@angular/core';
import { Router, RouterModule } from '@angular/router';

@Component({
  selector: 'app-test',
  standalone: true,
  imports: [RouterModule],
  templateUrl: './test.html',
  styleUrls: ['./test.css']
})
export class Test {
  private router = inject(Router); // ✅ Inject once at top

  logout(): void {
    localStorage.removeItem('authToken');
    this.router.navigate(['/login']);
  }
}
