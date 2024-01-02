import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {Tickets} from "../models/tickets";

@Injectable({
  providedIn: 'root'
})

export class TicketService {

  private REST_API_SERVER = "http://localhost:8080/api"
  constructor(private httpClient:HttpClient) { }

  public getAllTickets(): Observable<Array<Tickets>> {
    return this.httpClient.get<Array<Tickets>>(this.REST_API_SERVER + "/tickets", {headers: TicketService.getHeaders()})
  }

  public updateTicketStatus(ticketId: string, status: string): Observable<Array<Tickets>> {
    return this.httpClient.post<Array<Tickets>>(this.REST_API_SERVER + "/updateTicketStatus/" + ticketId+ "/" + status,{},{headers: TicketService.getHeaders()} );
  }

  public updateTicket(ticket: Tickets): Observable<Tickets> {
    return this.httpClient.post<Tickets>(this.REST_API_SERVER + "/updateTicket",ticket,{headers: TicketService.getHeaders()});
  }

  public addTicket(ticket: Tickets): Observable<Tickets> {
    return this.httpClient.post<Tickets>(this.REST_API_SERVER + "/add",ticket,{headers: TicketService.getHeaders()} );
  }

  public deleteTicket(ticketId: string): Observable<Tickets> {
    return this.httpClient.post<Tickets>(this.REST_API_SERVER + "/delete/" + ticketId,{headers: TicketService.getHeaders()});
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
