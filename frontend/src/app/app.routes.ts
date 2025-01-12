import {RouterModule, Routes} from '@angular/router';
import {RegisterPage} from './pages/register/register.page';
import {LoginPage} from './pages/login/login.page';
import {ChatPage} from './pages/chat/chat.page';
import {AuthGuard} from './guards/auth.guard';
import {UnAuthGuard} from './guards/unauth.guard';

export const routes: Routes = [
  {path: 'register', component: RegisterPage, canActivate: [UnAuthGuard]},
  {path: 'login', component: LoginPage, canActivate: [UnAuthGuard]},
  { path: 'chat', component: ChatPage, canActivate: [AuthGuard] },
  { path: '**', redirectTo: 'chat' },
];

export const AppRoutingModule = RouterModule.forRoot(routes);
