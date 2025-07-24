import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';

export const authGuard: CanActivateFn = (route, state) => {
  const isLoggedIn = localStorage.getItem('authToken') !== null;
console.log("isLoggedIn"+isLoggedIn+" "+localStorage.getItem('authToken'));
  if (!isLoggedIn) {
    // redirect to login
    const router = inject(Router);
    router.navigate(['/login']);
    return false;
  }

  return true;
};
