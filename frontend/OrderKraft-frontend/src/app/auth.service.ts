import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { jwtDecode } from 'jwt-decode';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private loginUrl = 'http://localhost:8081/api/auth/login';
  private resetPasswordUrl = 'http://localhost:8081/users/users/reset-password'; // ✅ Based on UserController

  constructor(private http: HttpClient) {}

  /**
   * Sends login request.
   */
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




  /**
   * Retrieves the JWT token.
   */
  getToken(): string | null {
  if (this.isBrowser()) {
    return localStorage.getItem('authToken');
  }
  return null;
}


  /**
   * Decodes the JWT token payload.
   */
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


  /**
   * Returns the user's role from JWT.
   */
  getRole(): string | null {
    const decoded = this.getDecodedToken();
    return decoded?.role || null;
  }

  /**
   * Returns the user's email from JWT.
   */
  getEmail(): string | null {
    const decoded = this.getDecodedToken();
    return decoded?.email || null;
  }

  /**
   * Returns the username or subject.
   */
  getUsername(): string | null {
    const decoded = this.getDecodedToken();
    return decoded?.sub || null;
  }

  /**
   * Checks if user is authenticated (token exists and not expired).
   */
  isAuthenticated(): boolean {
    const decoded = this.getDecodedToken();
    if (!decoded) return false;

    const currentTime = Math.floor(Date.now() / 1000);
    return decoded.exp && decoded.exp > currentTime;
  }

  /**
   * Calls reset password API on first login.
   */
  resetPassword(data: {
  oldPassword: string;
  newPassword: string;
  confirmPassword: string;
}): Observable<any> {
  const url = 'http://localhost:8081/users/users/reset-password';

  return this.http.post(url, data, {
    headers: {
      Authorization: `Bearer ${this.getToken()}`
    }
  });
}


  /**
   * Optional utility - check if this code runs in browser.
   */
  isBrowser(): boolean {
    return typeof window !== 'undefined' && typeof localStorage !== 'undefined';
  }
}
