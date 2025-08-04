import { Routes } from '@angular/router';
import { UserRegistration } from './user-registration/user-registration';
import { LoginPage } from './login-page/login-page';
import { Test } from './test/test';
import { ProductionManagerPage } from './dashboard/production-manager/production-manager';
import { authGuard } from './auth-guard';
import { ForgotPasswordPage } from './pages/forgot-password.page';
import { Unauthorized } from './pages/unauthorized/unauthorized';
import { roleGuard } from './auth/role-guard';


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
    canActivate: [authGuard]
  }
,{
  path:"forgotPassword",
  component :ForgotPasswordPage
},
  {
  path: 'user-registration',
  component:UserRegistration,
  // canActivate: [authGuard]
  canActivate: [roleGuard],
  data: { roles: ['ADMIN','Admin']}
  

},
{ path: 'production-manager', 
  component: ProductionManagerPage,
  // canActivate: [authGuard] 
  canActivate: [roleGuard],
  data: { roles: ['PRODUCTION-MANAGER']}

  },
  {
    path:'unauthorized',
    component: Unauthorized
  }
];
