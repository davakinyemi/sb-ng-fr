import { Component } from '@angular/core';
import {KeycloakService} from '../../services/keycloak/keycloak.service';

@Component({
  selector: 'app-secured',
  standalone: false,
  templateUrl: './secured.component.html',
  styleUrl: './secured.component.css'
})
export class SecuredComponent {
  constructor(private keycloakService: KeycloakService) {
  }

  async logout() {
    await this.keycloakService.logout();
  }

  async manageAccount() {
    await this.keycloakService.manageAccount();
  }
}
