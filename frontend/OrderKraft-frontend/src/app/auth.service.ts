import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private loginUrl = 'http://localhost:8081/api/auth/login';
  private userInfoUrl = 'http://localhost:8081/api/auth/user-info';

  private user: any = null;

  constructor(private http: HttpClient) {}

  login(credentials: { email: string; password: string }): Observable<any> {
     this.fetchUserInfo();
    //  console.log("")
    return this.http.post(this.loginUrl, credentials, {
      withCredentials: true,
    });
  }

<<<<<<< HEAD
  // Call this once after login to cache user info
  fetchUserInfo(): Observable<any> {
    console.log("test")
    return this.http.get(this.userInfoUrl, {
      withCredentials: true
    }).pipe(
      tap((data) => this.user = data),
      catchError(() => {
        this.user = null;
        return of(null);
      })
    );
=======

  saveToken(token: string): void {
  if (this.isBrowser()) {
    localStorage.setItem('authToken', token);
>>>>>>> 1b9fdddfda918b797d84f7a5c7b1d3490a3917a4
  }
}
logout(): void {
  if (this.isBrowser()) {
    localStorage.clear();
    window.location.href = '/login'; // force redirect
  }
}




<<<<<<< HEAD
  getRole(): string | null {
    return this.user?.role || null;
=======
  getToken(): string | null {
  if (this.isBrowser()) {
    return localStorage.getItem('authToken');
>>>>>>> 1b9fdddfda918b797d84f7a5c7b1d3490a3917a4
  }
  return null;
}


<<<<<<< HEAD
  getEmail(): string | null {
    return this.user?.email || null;
=======


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
>>>>>>> 1b9fdddfda918b797d84f7a5c7b1d3490a3917a4
  }
  return null;
}


  getUsername(): string | null {
    return this.user?.username || null;
  }

  isAuthenticated(): boolean {
    return !!this.user; // user is present if authenticated
  }

<<<<<<< HEAD
  logout(): void {
    this.user = null;
    // Ideally call backend logout to clear cookie
    window.location.href = '/login';
  }
=======



isBrowser(): boolean {
  return typeof window !== 'undefined' && typeof localStorage !== 'undefined';
}

getUsername(): string | null {
  const decoded = this.getDecodedToken();
    return decoded?.sub || null; // Or change to 'username' if backend provides it under that key
}

>>>>>>> 1b9fdddfda918b797d84f7a5c7b1d3490a3917a4
}
