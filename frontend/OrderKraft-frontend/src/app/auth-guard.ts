import { inject, PLATFORM_ID } from '@angular/core';
import { isPlatformBrowser } from '@angular/common';
import { CanActivateFn, Router } from '@angular/router';

export const authGuard: CanActivateFn = (route, state) => {
  const platformId = inject(PLATFORM_ID);

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
