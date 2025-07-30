import { Routes } from '@angular/router';
import { UserRegistration } from './user-registration/user-registration';
import { LoginPage } from './login-page/login-page';
import { Test } from './test/test';
import { ProductionManagerPage } from './production-manager/production-manager';
import { authGuard } from './auth-guard';
<<<<<<< HEAD

export const routes: Routes = [
  { path: 'login', component: LoginPage },
  { path: 'production-manager', component: ProductionManagerPage, canActivate: [authGuard] },
  { path: 'test', component: Test, canActivate: [authGuard] },
  { path: '', redirectTo: 'login', pathMatch: 'full' }
];




=======
import { ForgotPasswordPage } from './pages/forgot-password.page';
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
  

}
<<<<<<< HEAD

=======
>>>>>>> 221e8a70c63a4b41aa1e2d07ee1641a5b75e43b3
];
>>>>>>> 8f6f0c693a8eaaa8a60d88e57284d6acdd2fd137
