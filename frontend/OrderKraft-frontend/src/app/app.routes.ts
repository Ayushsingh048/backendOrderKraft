import { Routes } from '@angular/router';
import { LoginPage } from './login-page/login-page';
import { Test } from './test/test';
<<<<<<< HEAD
import { ProductionManagerPage } from './production-manager/production-manager';

export const routes: Routes = [{
    path: 'login',component: LoginPage},
  // {
  //   path: '',
  //   redirectTo: '/login',
  //   pathMatch: 'full'
  // },
  {path:'test',component:Test},
  { path: 'manager', component: ProductionManagerPage }, // ✅ add route
  { path: '', redirectTo: '/manager', pathMatch: 'full' } // ✅ temporary default
];
=======
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
  }];
>>>>>>> 86979af252453d82bcc5fdeeb12781f3028a47e5
