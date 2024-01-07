import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { Tickets } from '../../../models/tickets';
import { TicketService } from '../../../services/ticket.service';
import { UserService } from '../../../services/user.service';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { Project } from '../../../models/project';

@Component({
  selector: 'app-new-ticket',
  templateUrl: './new-ticket.component.html',
  styleUrl: './new-ticket.component.css',
})
export class NewTicketComponent implements OnInit {
  errorMessage: string | null = null;
  ticket: Tickets = new Tickets();
  ticketForm!: FormGroup;

  constructor(
    private ticketService: TicketService,
    public dialogRef: MatDialogRef<NewTicketComponent>,
    public userService: UserService,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) {
    if (data.ticket) {
      this.ticket = data.ticket;
    } else {
      this.ticket.project = new Project();
    }
    if (data.project_id) {
      if (!this.ticket.project) {
        this.ticket.project = new Project();
      }
      this.ticket.project.id = data.project_id;
    }
  }

  statusArrayList: string[] = ['OPEN', 'INPROGRESS', 'REVIEW', 'DONE'];

  ngOnInit() {
    this.ticketForm = new FormGroup({
      title: new FormControl('', Validators.required),
      description: new FormControl(''),
      status: new FormControl(''),
    });
  }

  onDialogSave(title: string, description: string, status: string) {
    this.ticket.title = title;
    this.ticket.description = description;
    this.ticket.status = status;

    if (!this.ticket.project || this.data.project_id) {
      this.ticket.project = new Project();
      this.ticket.project.id = this.data.project_id;
    }

    if (this.ticket.id == null || this.ticket.id == '') {
      this.ticketService.addTicket(this.ticket).subscribe((result) => {
        if (result) {
          this.dialogRef.close();
        }
      });
    } else {
      this.ticketService.updateTicket(this.ticket).subscribe((result) => {
        if (result) {
          this.dialogRef.close();
        }
      });
    }

    const ticketOperation = this.ticket.id
      ? this.ticketService.updateTicket(this.ticket)
      : this.ticketService.addTicket(this.ticket);

    ticketOperation.subscribe({
      next: (result) => {
        if (result) {
          this.dialogRef.close();
        }
      },
      error: (err) => {
        if (err.status === 422) {
          this.errorMessage = err.error;
        } else {
          this.errorMessage = 'An unexpected error occurred.';
        }
      },
    });
  }

  onDialogClose() {
    this.dialogRef.close();
  }
}
