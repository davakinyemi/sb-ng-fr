import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {provideHttpClient, withInterceptors} from '@angular/common/http';
import { ActivateComponent } from './pages/activate/activate.component';
import {CodeInputModule} from 'angular-code-input';
import {httpTokenInterceptor} from './services/interceptor/http-token.interceptor';
import { SecuredComponent } from './pages/secured/secured.component';

@NgModule({
  declarations: [
    AppComponent,
    ActivateComponent,
    SecuredComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    CodeInputModule
  ],
  providers: [
    provideHttpClient(
      withInterceptors([httpTokenInterceptor])
    )
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
