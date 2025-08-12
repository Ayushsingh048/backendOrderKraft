import { inject, PLATFORM_ID } from '@angular/core';
import { isPlatformBrowser } from '@angular/common';
import { CanActivateFn, Router } from '@angular/router';
import { AuthService } from '../auth.service';

export const roleGuard: CanActivateFn = (route, state) => {
  const platformId = inject(PLATFORM_ID);
  const router = inject(Router);
  const authService = inject(AuthService);

  if (isPlatformBrowser(platformId)) {
    const expectedRoles = route.data['roles'] as string[];
    const userRole = authService.getRole()?.toUpperCase();
    console.log("userRole before checking"+userRole);
    console.log("expected role checking"+expectedRoles);
    if (authService.isAuthenticated() && expectedRoles.includes(userRole!)) {
      return true;
    }

    // If role doesn't match, navigate to unauthorized page
    console.log("check not complete");
    router.navigate(['/unauthorized']);
    return false;
  }

  // ✅ On the server (SSR): Skip role-based logic to prevent localStorage errors
  return true;
};
