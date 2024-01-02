import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {Tickets} from "../../../../models/tickets";
import {TicketService} from "../../../../services/ticket.service";
import {User} from "../../../../models/user";
import {UserService} from "../../../../services/user.service";
import {Observable} from "rxjs";

@Component({
  selector: 'app-new-ticket',
  templateUrl: './new-ticket.component.html',
  styleUrl: './new-ticket.component.css'
})
export class NewTicketComponent implements OnInit {

  ticket: Tickets = new Tickets();


  constructor(
    private ticketService: TicketService,
    public dialogRef: MatDialogRef<NewTicketComponent>,
    public userService: UserService,
    @Inject(MAT_DIALOG_DATA) data : Tickets) {
    this.ticket = data;
  }

  statusArrayList: string[] = ['OPEN', 'INPROGRESS', 'REVIEW', 'DONE'];


  ngOnInit(){

  }

  onDialogSave(title: string, description: string, status: string) {
    if (this.ticket.ticket_id == null || this.ticket.ticket_id == "") {
      let ticket = new Tickets();
      ticket.title = title;
      ticket.description = description;
      ticket.status = status;
      this.ticketService.addTicket(ticket).subscribe(result => {
        if (result) {
          this.dialogRef.close();
        }
      });
    } else {
      this.ticketService.updateTicket(this.ticket).subscribe(result => {
        if (result) {
          this.dialogRef.close();
        }
      })
    }
  }

  onDialogClose() {
    this.dialogRef.close();
  }
}
