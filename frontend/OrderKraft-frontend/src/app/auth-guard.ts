import { inject, PLATFORM_ID } from '@angular/core';
import { isPlatformBrowser } from '@angular/common';
import { CanActivateFn, Router } from '@angular/router';

export const authGuard: CanActivateFn = () => {
  const platformId = inject(PLATFORM_ID);
  const router = inject(Router);

  // ✅ Only access localStorage in the browser
  if (isPlatformBrowser(platformId)) {
    const token = localStorage.getItem('authToken');
    if (!token) {
      router.navigate(['/login']);
      return false;
    }
    return true;
  }

  // ⚠️ On the server: skip localStorage check
  return true;
};
