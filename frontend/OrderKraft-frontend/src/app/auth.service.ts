import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
  
})
export class AuthService {
  
  private loginUrl = 'http://localhost:8081/api/auth/login';
  
  private resetPasswordUrl = 'http://localhost:8081/users/users/reset-password'; // ✅ Based on UserController
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
  }

// logout(): void {
//   if (this.isBrowser()) {
//     localStorage.clear();
//     window.location.href = '/login'; // force redirect
//   }
// }

  initUser(): Promise<any> {
  return this.fetchUserInfo().toPromise();
}


  getRole(): string | null {
    return this.user?.role || null;
  }



  getEmail(): string | null {
    return this.user?.email || null;
  }

  getUsername(): string | null {
    return this.user?.username || null;
  }

  isAuthenticated(): boolean {
    return !!this.user; // user is present if authenticated
  }

  logout(): void {
    this.user = null;
    // Ideally call backend logout to clear cookie
    window.location.href = '/login';
  }

  /**
   * ✅ Calls reset password API on first login (cookie-based auth).
   */
  resetPassword(data: {
    oldPassword: string;
    newPassword: string;
    confirmPassword: string;
  }): Observable<any> {
    return this.http.post(this.resetPasswordUrl, data, {
      withCredentials: true // ✅ send session cookie
    });
  }
}
