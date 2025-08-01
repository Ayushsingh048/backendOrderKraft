import { inject } from '@angular/core';
import { HttpInterceptorFn } from '@angular/common/http';
import { AuthService } from '../auth.service';

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  // Inject AuthService to access the token
  const authService = inject(AuthService);

  // Get token from local storage via AuthService
  const token = authService.getToken();

  // If token exists, clone the request and add Authorization header
  const authReq = token
    ? req.clone({
        setHeaders: {
          Authorization: `Bearer ${token}`
        }
      })
    : req;

  // Pass the modified or original request to the next handler
  return next(authReq);
};
