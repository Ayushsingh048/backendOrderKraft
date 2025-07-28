import { Routes } from '@angular/router';
import { UserRegistration } from './user-registration/user-registration';
import { LoginPage } from './login-page/login-page';
import { Test } from './test/test';
import { authGuard } from './auth-guard';
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
  },
  {
  path: 'user-registration',
  component:UserRegistration,
  // canActivate: [authGuard]
  

},
];
