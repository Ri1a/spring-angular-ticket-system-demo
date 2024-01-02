import { Component, OnInit } from '@angular/core';
import {UserService} from "../../services/user.service";
import {AuthenticationService} from "../../services/authentication.service";

@Component({
  selector: 'app-log-in',
  templateUrl: './log-in.component.html',
  styleUrl: './log-in.component.css'
})
export class LogInComponent implements OnInit {

  hide: boolean = true;

  constructor(private userService: UserService, private authService: AuthenticationService) { }

  ngOnInit(): void {
  }

  logInUser(username: string, password: string ) {
    this.authService.authenticate(username, password).subscribe(result => console.log(result));
  }
}
