import { Component } from '@angular/core';
import {Router} from '@angular/router';
import {AuthenticationService} from '../../services/services/authentication.service';

@Component({
  selector: 'app-input-code',
  standalone: false,
  templateUrl: './activate.component.html',
  styleUrl: './activate.component.css'
})
export class ActivateComponent {
  message: string = '';
  isOkay: boolean = true;
  submitted: boolean = false;

  constructor(
    private router: Router,
    private authService: AuthenticationService
  ) {}

  onCodeCompleted(token: string) {
    this.confirm(token);
  }

  private confirm(token: string) {
    this.authService.confirm({
      token
    }).subscribe({
      next: () => {
        this.message = "Successfully activated."
        this.submitted = true;
        this.isOkay = true;
      },
      error: () => {
        this.message = "Token expired or invalid."
        this.submitted = true;
        this.isOkay = false;
      }
    });
  }
}
