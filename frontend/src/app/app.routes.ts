import {RouterModule, Routes} from '@angular/router';
import {RegisterPage} from './pages/register/register.page';
import {LoginPage} from './pages/login/login.page';

export const routes: Routes = [
  {path: 'register', component: RegisterPage},
  {path: 'login', component: LoginPage},
  {path: '', redirectTo: 'login', pathMatch: 'full'}
];

export const AppRoutingModule = RouterModule.forRoot(routes);
