import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { User } from '../models/user';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  private REST_API_SERVER = 'http://localhost:8080/api/users';
  constructor(private httpClient: HttpClient) {}

  public getAllUser(): Observable<Array<User>> {
    return this.httpClient.get<Array<User>>(this.REST_API_SERVER, {});
  }

  public updateUser(user: User): Observable<User> {
    return this.httpClient.post<User>(
      this.REST_API_SERVER + '/' + user.id + '/edit',
      user,
      {}
    );
  }

  public saveUser(user: User): Observable<User> {
    return this.httpClient.post<User>(this.REST_API_SERVER + '/add', user, {});
  }

  public deleteUser(userID: string): Observable<User> {
    return this.httpClient.post<User>(
      this.REST_API_SERVER + '/' + userID + '/delete',
      {}
    );
  }
}
