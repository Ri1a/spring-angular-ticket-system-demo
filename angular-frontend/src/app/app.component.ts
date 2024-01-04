import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from './services/authentication.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.css',
})
export class AppComponent implements OnInit {
  title = 'angular-frontend';

  isUserLoggedIn: boolean = false;
  isAdminUser: boolean = false;

  constructor(private authenticationService: AuthenticationService) {}

  ngOnInit(): void {
    this.isUserLoggedIn = this.authenticationService.isUserLoggedIn();
    this.isAdminUser = this.authenticationService.isUserAdmin();
  }

  logoutUser(): void {
    this.authenticationService.logOut();
  }
}
