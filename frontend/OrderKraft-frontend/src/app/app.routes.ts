import { Routes } from '@angular/router';
import { LoginPage } from './login-page/login-page';
import { Test } from './test/test';
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
