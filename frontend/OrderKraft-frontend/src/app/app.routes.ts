import { Routes } from '@angular/router';
import { UserRegistration } from './user-registration/user-registration';
import { LoginPage } from './login-page/login-page';
import { Test } from './test/test';
import { ProductionManagerPage } from './dashboard/production-manager/production-manager';
import { authGuard } from './auth-guard';
import { ForgotPasswordPage } from './pages/forgot-password.page';

export const routes: Routes = [
  { path: 'login', component: LoginPage },
  { path: 'production-manager', component: ProductionManagerPage, canActivate: [authGuard] },
  { path: 'test', component: Test, canActivate: [authGuard] },
  {path:"forgotPassword",component :ForgotPasswordPage},
  {path: 'user-registration',component:UserRegistration},// canActivate: [authGuard]
  { path: '', redirectTo: 'login', pathMatch: 'full' }
];
// import { OtpPagePage } from './pages/otp-page.page';

