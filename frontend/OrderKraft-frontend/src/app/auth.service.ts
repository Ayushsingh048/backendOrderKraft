import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private loginUrl = 'http://localhost:8081/api/auth/login'; //  Replace with real API

  constructor(private http: HttpClient) {}

  login(credentials: { email: string; password: string }): Observable<any> {
    return this.http.post(this.loginUrl, credentials);
  }

  saveToken(token: string): void {
    console.log(token);
    localStorage.setItem('authToken', token);
  }

  getToken(): string | null {
    return localStorage.getItem('authToken');
  }

 saveUserInfo(username: string, role: string, email: string): void {
  localStorage.setItem('username', username);
  localStorage.setItem('role', role);
  localStorage.setItem('email', email);
}



getRole(): string | null {
  return localStorage.getItem('role');
}

getEmail(): string | null {
  return localStorage.getItem('email');
}

logout(): void {
  localStorage.clear();
  window.location.href = '/login'; // force redirect
}
isBrowser(): boolean {
  return typeof window !== 'undefined' && typeof localStorage !== 'undefined';
}

getUsername(): string | null {
  return this.isBrowser() ? localStorage.getItem('username') : null;
}

}
