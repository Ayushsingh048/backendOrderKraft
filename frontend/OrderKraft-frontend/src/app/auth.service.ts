import { Injectable } from '@angular/core';
<<<<<<< HEAD
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { jwtDecode } from 'jwt-decode';
=======
import { HttpClient } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';
>>>>>>> 8e8d81f2ee3fe293a87f4ab506dde54aa80ad0a2

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private loginUrl = 'http://localhost:8081/api/auth/login';
<<<<<<< HEAD
  private resetPasswordUrl = 'http://localhost:8081/users/users/reset-password'; // ✅ Based on UserController
=======
  private userInfoUrl = 'http://localhost:8081/api/auth/user-info';

  private user: any = null;
>>>>>>> 8e8d81f2ee3fe293a87f4ab506dde54aa80ad0a2

  constructor(private http: HttpClient) {}

  /**
   * Sends login request.
   */
  login(credentials: { email: string; password: string }): Observable<any> {
<<<<<<< HEAD
    return this.http.post(this.loginUrl, credentials);
=======
     this.fetchUserInfo();
    //  console.log("")
    return this.http.post(this.loginUrl, credentials, {
      withCredentials: true,
    });
>>>>>>> 8e8d81f2ee3fe293a87f4ab506dde54aa80ad0a2
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

<<<<<<< HEAD



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
=======
// logout(): void {
//   if (this.isBrowser()) {
//     localStorage.clear();
//     window.location.href = '/login'; // force redirect
//   }
// }




  getRole(): string | null {
    return this.user?.role || null;
>>>>>>> 8e8d81f2ee3fe293a87f4ab506dde54aa80ad0a2
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

<<<<<<< HEAD
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
=======

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

}
>>>>>>> 8e8d81f2ee3fe293a87f4ab506dde54aa80ad0a2
