import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { User } from '../models/user.model';
import { Role } from '../models/role.model';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  private userApiUrl = 'http://localhost:8081/users/add';
  private roleApiUrl = 'http://localhost:8081/roles/all';

  constructor(private http: HttpClient) {}

  registerUser(user: User): Observable<any> {
    return this.http.post(this.userApiUrl, user);
  }

  getRoles(): Observable<Role[]> {
    return this.http.get<Role[]>(this.roleApiUrl);
  }
}
