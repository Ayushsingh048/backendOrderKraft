import { inject, PLATFORM_ID } from '@angular/core';
import { isPlatformBrowser } from '@angular/common';
import { CanActivateFn, Router } from '@angular/router';
import { AuthService } from './auth.service';
import { PLATFORM_ID } from '@angular/core';
import { isPlatformBrowser } from '@angular/common';

export const authGuard: CanActivateFn = (route, state) => {
  const platformId = inject(PLATFORM_ID);
<<<<<<< HEAD

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
=======
>>>>>>> 562a465eeb9a541ed1ce930b3510dfd009fef285

  if (isPlatformBrowser(platformId)) {
    const isLoggedIn = localStorage.getItem('authToken') !== null;
    console.log("isLoggedIn: " + isLoggedIn + " Token: " + localStorage.getItem('authToken'));

    if (!isLoggedIn) {
      const router = inject(Router);
      router.navigate(['/login']);
      return false;
    }

    return true;
  } else {
    // During SSR: prevent accessing localStorage
    console.warn("SSR: Skipping auth guard check");
    return true; // optionally true if you want to render login first on server
  }
};
