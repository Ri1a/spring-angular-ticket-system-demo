import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {User} from "../models/user";

@Injectable({
  providedIn: 'root'
})

export class UserService {

  private REST_API_SERVER = "http://localhost:8080/api/users"
  constructor(private httpClient:HttpClient) { }

  public getAllUser(): Observable<Array<User>> {
    return this.httpClient.get<Array<User>>(this.REST_API_SERVER, {headers: UserService.getHeaders()})
  }

  public updateUser(user: User): Observable<User> {
    return this.httpClient.put<User>(this.REST_API_SERVER + "/" + user.user_id + "/edit",user,{headers: UserService.getHeaders()});
  }

  public saveUser(user: User): Observable<User> {
    return this.httpClient.post<User>(this.REST_API_SERVER + "/add",user,{headers: UserService.getHeaders()} );
  }

  public deleteUser(userID: string): Observable<User> {
    return this.httpClient.post<User>(this.REST_API_SERVER + "/" + userID + "/delete",{headers: UserService.getHeaders()});
  }

  public static getHeaders(contentType: string = 'application/json'): HttpHeaders {
    let headers = new HttpHeaders().set('Content-Type', contentType);
    headers = headers.append('Cache-Control', 'no-cache');
    headers = headers.append('Pragma', 'no-cache');
    headers = headers.append('Access-Control-Allow-Origin', 'http://localhost:4200');
    headers =  headers.append('Access-Control-Allow-Headers', "Origin, X-Requested-With, Content-Type, Accept");
    return headers;
  }
}
