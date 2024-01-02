import {Component, OnInit} from '@angular/core';
import {TicketService} from "../../../../services/ticket.service";
import {Tickets} from "../../../../models/tickets";
import {CdkDragDrop, moveItemInArray, transferArrayItem} from "@angular/cdk/drag-drop";
import {MatDialog} from "@angular/material/dialog";
import {NewTicketComponent} from "../new-ticket/new-ticket.component";
import {User} from "../../../../models/user";


@Component({
  selector: 'app-overview',
  templateUrl: './overview.component.html',
  styleUrl: './overview.component.css'
})
export class OverviewComponent implements OnInit {
  ticketListOpen: Tickets[] = [];
  ticketListProcess: Tickets[] = [];
  ticketListReview: Tickets[] = [];
  ticketListDone: Tickets[] = [];

  OPEN: string = "Open";
  INPROGRESS: string = "In Progress";
  REVIEW: string = "Review";
  DONE: string = "Done";

  userList: User[] = [];

  currentMovedTicketId: string = "";

  constructor(private ticketService: TicketService,
              public dialog: MatDialog) {
  }

  ngOnInit(): void {
    this.loadAllTickets();
  }


  loadAllTickets(): void {
    this.ticketService.getAllTickets().subscribe(result => {
      this.ticketListOpen = result.filter(x => x.status === "OPEN");
      this.ticketListProcess = result.filter(x => x.status === "INPROGRESS");
      this.ticketListReview = result.filter(x => x.status === "REVIEW");
      this.ticketListDone = result.filter(x => x.status === "DONE");
    });
  }

  getCurrentTicket(ticketId: string): void{
    this.currentMovedTicketId = ticketId;
  }

  openDialogPopUp(ticket: Tickets) {
    const dialogRef = this.dialog.open(NewTicketComponent, {
      width: '500px',
      height: '450px',
      panelClass: 'my-dialog',
      data: ticket
    });

    dialogRef.afterClosed().subscribe(
      () =>   this.loadAllTickets()
    );
  }

  openDialogNewTicket() {
    const dialogRef = this.dialog.open(NewTicketComponent, {
      width: '500px',
      height: '450px',
      panelClass: 'my-dialog',
      data: new Tickets(),
    });

    dialogRef.afterClosed().subscribe(
      () =>   this.loadAllTickets()
    );
  }

  drop(event: CdkDragDrop<Tickets[], any>) {
    if(event.container.id != undefined) {
      if(this.currentMovedTicketId != null && this.currentMovedTicketId != undefined) {
        this.ticketService.updateTicketStatus(this.currentMovedTicketId, event.container.id).subscribe(result => console.log(result));
      }
    }
    if (event.previousContainer === event.container) {
      moveItemInArray(event.container.data, event.previousIndex, event.currentIndex);
    } else {
      transferArrayItem(event.previousContainer.data,
        event.container.data,
        event.previousIndex,
        event.currentIndex);
    }
  }

  deleteTicket(ticket_id: string) {
    this.ticketService.deleteTicket(ticket_id).subscribe(result => {
      this.loadAllTickets();
    })
  }
}
