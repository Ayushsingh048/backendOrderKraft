import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { jwtDecode } from 'jwt-decode';//jwt decode

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
  if (this.isBrowser()) {
    localStorage.setItem('authToken', token);
  }
}
logout(): void {
  if (this.isBrowser()) {
    localStorage.clear();
    window.location.href = '/login'; // force redirect
  }
}




  getToken(): string | null {
  if (this.isBrowser()) {
    return localStorage.getItem('authToken');
  }
  return null;
}




// Decode JWT token and extract payload
  getDecodedToken(): any {
  if (!this.isBrowser()) return null;

  const token = this.getToken();
  if (token) {
    try {
      return jwtDecode(token);
    } catch (error) {
      console.error('Invalid token');
      return null;
    }
  }
  return null;
}




  
//  saveUserInfo(username: string, role: string, email: string): void {
//   localStorage.setItem('username', username);
//   localStorage.setItem('role', role);
//   localStorage.setItem('email', email);
// }



getRole(): string | null {
  const decoded = this.getDecodedToken();
    return decoded?.role || null;
}



getEmail(): string | null {
  const decoded = this.getDecodedToken();
    return decoded?.email || null;
}
/**
   * Checks whether the user is authenticated and token is valid (not expired).
   */
  isAuthenticated(): boolean {
    const decoded = this.getDecodedToken();
    if (!decoded) return false;

    const currentTime = Math.floor(Date.now() / 1000); // current time in seconds
    return decoded.exp && decoded.exp > currentTime;
  }




isBrowser(): boolean {
  return typeof window !== 'undefined' && typeof localStorage !== 'undefined';
}

getUsername(): string | null {
  const decoded = this.getDecodedToken();
    return decoded?.sub || null; // Or change to 'username' if backend provides it under that key
}

}
