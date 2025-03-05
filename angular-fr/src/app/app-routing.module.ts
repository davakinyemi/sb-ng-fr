import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {ActivateComponent} from './pages/activate/activate.component';

const routes: Routes = [
  {
    path: 'activate',
    component: ActivateComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
