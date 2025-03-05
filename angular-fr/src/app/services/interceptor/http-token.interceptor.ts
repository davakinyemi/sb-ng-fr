import {HttpHeaders, HttpInterceptorFn} from '@angular/common/http';
import {inject} from '@angular/core';
import {TokenService} from '../token/token.service';
import {KeycloakService} from '../keycloak/keycloak.service';

export const httpTokenInterceptor: HttpInterceptorFn = (req, next) => {
  const token = inject(KeycloakService).keycloak.token;
  if (token) {
    const authReq = req.clone({
      headers: new HttpHeaders({
        Authorization: `Bearer ${token}`
      })
      /*headers: req.headers.append('Authorization', 'Bearer ' + token)*/
    });
    return next(authReq);
  }
  return next(req);
};
