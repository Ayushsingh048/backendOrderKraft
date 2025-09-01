import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class UserService {
  private baseUrl = 'http://localhost:8081/users/all';

  constructor(private http: HttpClient) {}

  registerUser(userData: any): Observable<any> {
    return this.http.post(this.baseUrl, userData);
  }

  getAllRoles(): Observable<any[]> {
    return this.http.get<any[]>('http://localhost:8081/roles/all');
  }
   checkEmailExists(email: string): Observable<boolean> {
    return this.http.get<boolean>(`${this.baseUrl}/users/check-email?email=${email}`);
  }
}
