import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {ActivateComponent} from './pages/activate/activate.component';
import {SecuredComponent} from './pages/secured/secured.component';
import {authGuard} from './services/guard/auth.guard';

const routes: Routes = [
  {
    path: 'activate',
    component: ActivateComponent
  },
  {
    path: 'secured',
    component: SecuredComponent,
    canActivate: [authGuard]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
