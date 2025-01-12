import { Routes, RouterModule } from '@angular/router';
import { RegisterComponent } from './register/register.component';

export const routes: Routes = [
  { path: 'register', component: RegisterComponent }
];

// The AppRoutingModule now imports RouterModule directly without the @NgModule decorator
export const AppRoutingModule = RouterModule.forRoot(routes);
