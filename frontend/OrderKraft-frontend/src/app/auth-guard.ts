import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { AuthService } from './auth.service';
import { PLATFORM_ID } from '@angular/core';
import { isPlatformBrowser } from '@angular/common';

export const authGuard: CanActivateFn = (route, state) => {
  const platformId = inject(PLATFORM_ID);

  if(isPlatformBrowser(platformId)){
  const isLoggedIn = inject(AuthService).getToken() !== null;
console.log("isLoggedIn"+isLoggedIn+" "+localStorage.getItem('authToken'));
  if (!isLoggedIn) {
    // redirect to login
    const router = inject(Router);
    router.navigate(['/login']);
    return false;
  }
}

  return true;
};
