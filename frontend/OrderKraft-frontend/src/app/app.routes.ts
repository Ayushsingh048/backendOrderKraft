import { Routes } from '@angular/router';
import { UserRegistration } from './user-registration/user-registration';
import { LoginPage } from './login-page/login-page';
import { Test } from './test/test';
import { ProductionManagerPage } from './dashboard/production-manager/production-manager';
import { authGuard } from './auth-guard';
import { ForgotPasswordPage } from './pages/forgot-password.page';
import { Unauthorized } from './pages/unauthorized/unauthorized';
import { roleGuard } from './auth/role-guard';
import { Admin } from './dashboard/admin/admin';
// import { OtpPagePage } from './pages/otp-page.page';
export const routes: Routes = [{
    path: 'login',
    component: LoginPage
  },
  {
    path: '',
    redirectTo: '/login',
    pathMatch: 'full'
  },{
    path:'test',
    component:Test,
    // canActivate: [authGuard]
  }
,{
  path:"forgotPassword",
  component :ForgotPasswordPage
},
  {
  path: 'user-registration',
  component:UserRegistration,
  // canActivate: [authGuard]
<<<<<<< HEAD
  // canActivate: [roleGuard],
  // data: { roles: ['ADMIN']}
=======
  canActivate: [roleGuard],
  data: { roles: ['ADMIN','Admin']}
>>>>>>> 1b9fdddfda918b797d84f7a5c7b1d3490a3917a4
  

},
{ path: 'production-manager', 
  component: ProductionManagerPage,
  // canActivate: [authGuard] 
  canActivate: [roleGuard],
  data: { roles: ['PRODUCTION_MANAGER']}

  },
  { path: 'admin', 
  component: Admin,
  // canActivate: [authGuard] 
  canActivate: [roleGuard],
  data: { roles: ['Admin','ADMIN']}

  },
  {
    path:'unauthorized',
    component: Unauthorized
  }
];
