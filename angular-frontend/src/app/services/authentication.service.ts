import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root',
})
export class AuthenticationService {
  private REST_API_SERVER = 'http://localhost:8080/api';
  constructor(private httpClient: HttpClient) {}

  authenticate(username: string, password: string) {
    const headers = new HttpHeaders({
      'Content-Type': 'application/x-www-form-urlencoded',
    });

    const body = `username=${encodeURIComponent(
      username
    )}&password=${encodeURIComponent(password)}`;

    return this.httpClient
      .post<any>(`${this.REST_API_SERVER}/login`, body, {
        headers: headers,
        responseType: 'text' as 'json',
      })
      .pipe(
        map((response) => {
          const token = response;
          sessionStorage.setItem('username', username);
          sessionStorage.setItem('token', token);

          window.location.href = 'http://localhost:4200/overview';
          return { token };
        })
      );
  }

  isUserLoggedIn() {
    if (typeof window !== 'undefined') {
      let user = sessionStorage.getItem('username');
      return !(user === null);
    }
    return false;
  }

  isUserAdmin(): boolean {
    if (typeof window !== 'undefined') {
      let userRole = sessionStorage.getItem('role');
      return userRole === 'Admin';
    }
    return false;
  }

  logOut() {
    if (typeof window !== 'undefined') {
      sessionStorage.removeItem('username');
      sessionStorage.clear();
      window.location.reload()
    }
  }
}
