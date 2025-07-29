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
  }
,{
  path:"forgotPassword",
  component :ForgotPasswordPage
},
<<<<<<< HEAD
{
=======
  {
>>>>>>> aa52a7e6f895f9cfa39dbd4c17d838812a08e332
  path: 'user-registration',
  component:UserRegistration,
  // canActivate: [authGuard]
  

}
];
<<<<<<< HEAD
  
=======
>>>>>>> aa52a7e6f895f9cfa39dbd4c17d838812a08e332
