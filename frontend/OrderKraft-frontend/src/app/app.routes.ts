import { Routes } from '@angular/router';
import { UserRegistration } from './user-registration/user-registration';
import { LoginPage } from './login-page/login-page';
import { Test } from './test/test';
import { authGuard } from './auth-guard';
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
<<<<<<< HEAD
  }
,{
  path:"forgotPassword",
  component :ForgotPasswordPage
}];
=======
  },
  {
  path: 'user-registration',
  component:UserRegistration,
  // canActivate: [authGuard]
  

},
];
>>>>>>> bdbba305526aba81c524bfedc3e8ef8b2514cb7a
