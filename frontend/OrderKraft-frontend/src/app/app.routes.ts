import { Routes } from '@angular/router';
import { LoginPage } from './login-page/login-page';
import { Test } from './test/test';
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
    component:Test
  }];
