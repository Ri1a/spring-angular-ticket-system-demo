import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {map} from "rxjs/operators";

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  private REST_API_SERVER = "http://localhost:8080/api"
  constructor(private httpClient: HttpClient) {}

  authenticate(username: string, password: string) {
    return this.httpClient
      .post<any>(this.REST_API_SERVER + "/auth", { username, password })
      .pipe(
        map(userData => {

          sessionStorage.setItem("username", username);
          let tokenStr = "Bearer " + userData.token;
          sessionStorage.setItem("token", tokenStr);
          sessionStorage.setItem("role", userData.role);

          window.location.href = "http://localhost:4200/overview";
          return userData;
        })
      );
  }

  isUserLoggedIn() {
    let user = sessionStorage.getItem("username");
    return !(user === null);
  }

  isUserAdmin(): boolean {
    let userRole = sessionStorage.getItem("role");
    if(userRole == "Admin")
      return true;
    else
      return false;
  }

  logOut() {
    sessionStorage.removeItem("username");
  }
}
