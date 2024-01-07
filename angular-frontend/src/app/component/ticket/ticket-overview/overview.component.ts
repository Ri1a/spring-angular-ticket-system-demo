import { Component, OnInit } from '@angular/core';
import { TicketService } from '../../../services/ticket.service';
import { Tickets } from '../../../models/tickets';
import {
  CdkDragDrop,
  moveItemInArray,
  transferArrayItem,
} from '@angular/cdk/drag-drop';
import { MatDialog } from '@angular/material/dialog';
import { NewTicketComponent } from '../new-ticket/new-ticket.component';
import { User } from '../../../models/user';
import { ProjectService } from '../../../services/project.service';
import { ActivatedRoute } from '@angular/router';
import { Project } from '../../../models/project';
import { BreakpointObserver, Breakpoints } from '@angular/cdk/layout';

@Component({
  selector: 'app-overview',
  templateUrl: './overview.component.html',
  styleUrl: './overview.component.css',
})
export class OverviewComponent implements OnInit {
  ticketListOpen: Tickets[] = [];
  ticketListProcess: Tickets[] = [];
  ticketListReview: Tickets[] = [];
  ticketListDone: Tickets[] = [];

  OPEN: string = 'Open';
  INPROGRESS: string = 'In Progress';
  REVIEW: string = 'Review';
  DONE: string = 'Done';

  userList: User[] = [];

  project: Project = new Project();
  projectId: string = '';

  currentMovedTicketId: string = '';
  currentMovedTicket?: Tickets;

  layoutMode: string = 'four-columns';

  constructor(
    private ticketService: TicketService,
    public dialog: MatDialog,
    private projectService: ProjectService,
    private activatedRoute: ActivatedRoute,
    private breakpointObserver: BreakpointObserver
  ) {}

  ngOnInit(): void {
    this.breakpointObserver
      .observe([
        Breakpoints.XSmall,
        Breakpoints.Small,
        Breakpoints.Medium,
        Breakpoints.Large,
        Breakpoints.XLarge
      ])
      .subscribe(result => {
        if (result.breakpoints[Breakpoints.XSmall]) {
          this.layoutMode = 'one-column';
        } else if (result.breakpoints[Breakpoints.Small]) {
          this.layoutMode = 'two-columns';
        } else if (result.breakpoints[Breakpoints.Medium]) {
          this.layoutMode = 'two-columns';
        } else {
          this.layoutMode = 'four-columns';
        }
      });
    this.activatedRoute.params.subscribe((params) => {
      this.projectId = params['id'];
    });
    this.loadProjectTickets();
  }

  loadProjectTickets(): void {
    this.projectService.showProject(this.projectId).subscribe((project) => {
      this.project = project;
      this.ticketListOpen = project.tickets.filter((x) => x.status === 'OPEN');
      this.ticketListProcess = project.tickets.filter(
        (x) => x.status === 'INPROGRESS'
      );
      this.ticketListReview = project.tickets.filter(
        (x) => x.status === 'REVIEW'
      );
      this.ticketListDone = project.tickets.filter((x) => x.status === 'DONE');
    });
  }

  getCurrentTicket(ticket: Tickets): void {
    this.currentMovedTicketId = ticket.id;
    this.currentMovedTicket = ticket;
  }

  openDialogPopUp(ticket: Tickets) {
    const dialogRef = this.dialog.open(NewTicketComponent, {
      width: '500px',
      height: '450px',
      panelClass: 'my-dialog',
      data: { ticket: ticket, project_id: this.projectId },
    });

    dialogRef.afterClosed().subscribe(() => this.loadProjectTickets());
  }

  openDialogNewTicket() {
    const dialogRef = this.dialog.open(NewTicketComponent, {
      width: '500px',
      height: '450px',
      panelClass: 'my-dialog',
      data: { project_id: this.projectId },
    });

    dialogRef.afterClosed().subscribe(() => this.loadProjectTickets());
  }

  drop(event: CdkDragDrop<Tickets[], any>) {
    if (event.container.id != undefined) {
      if (
        this.currentMovedTicket != null &&
        this.currentMovedTicket != undefined
      ) {
        this.currentMovedTicket.status = event.container.id;
        this.ticketService
          .updateTicket(this.currentMovedTicket)
          .subscribe((result) => console.log(result));
      }
    }
    if (event.previousContainer === event.container) {
      moveItemInArray(
        event.container.data,
        event.previousIndex,
        event.currentIndex
      );
    } else {
      transferArrayItem(
        event.previousContainer.data,
        event.container.data,
        event.previousIndex,
        event.currentIndex
      );
    }
  }

  deleteTicket(ticket_id: string) {
    this.ticketService.deleteTicket(ticket_id).subscribe((result) => {
      this.loadProjectTickets();
    });
  }
}
