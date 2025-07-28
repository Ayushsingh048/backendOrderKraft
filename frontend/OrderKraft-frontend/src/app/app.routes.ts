import { Routes } from '@angular/router';
import { LoginPage } from './login-page/login-page';
import { Test } from './test/test';
import { ProductionManagerPage } from './production-manager/production-manager';
import { authGuard } from './auth-guard';
export const routes: Routes = [{
    path: 'login',component: LoginPage},
  {path: '',redirectTo: '/login',pathMatch: 'full'},
  { path:'test',component:Test,canActivate: [authGuard]},
   { path: 'manager', component: ProductionManagerPage }

];



