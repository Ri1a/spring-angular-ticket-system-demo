import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Tickets } from '../models/tickets';

@Injectable({
  providedIn: 'root',
})
export class TicketService {
  private REST_API_SERVER = 'http://localhost:8080/api/tickets';
  constructor(private httpClient: HttpClient) {}

  public getAllTickets(): Observable<Array<Tickets>> {
    return this.httpClient.get<Array<Tickets>>(this.REST_API_SERVER, {
      /*headers: TicketService.getHeaders()*/
    });
  }

  public updateTicketStatus(
    ticketId: string,
    status: string
  ): Observable<Array<Tickets>> {
    return this.httpClient.put<Array<Tickets>>(
      this.REST_API_SERVER + '/' + ticketId + '/status',
      {}
    );
  }

  public updateTicket(ticket: Tickets): Observable<Tickets> {
    return this.httpClient.post<Tickets>(
      this.REST_API_SERVER + ticket.ticket_id + '/update',
      ticket,
      {}
    );
  }

  public addTicket(ticket: Tickets): Observable<Tickets> {
    return this.httpClient.post<Tickets>(
      this.REST_API_SERVER + '/add',
      ticket,
      {}
    );
  }

  public deleteTicket(ticketId: string): Observable<Tickets> {
    return this.httpClient.post<Tickets>(
      this.REST_API_SERVER + ticketId + '/delete',
      {}
    );
  }
}
