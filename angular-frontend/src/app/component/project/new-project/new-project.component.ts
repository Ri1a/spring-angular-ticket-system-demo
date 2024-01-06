import {Component, Inject} from '@angular/core';
import {Project} from "../../../models/project";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {UserService} from "../../../services/user.service";
import {ProjectService} from "../../../services/project.service";
import {TicketService} from "../../../services/ticket.service";

@Component({
  selector: 'app-new-project',
  templateUrl: './new-project.component.html',
  styleUrl: './new-project.component.css'
})
export class NewProjectComponent {
  project: Project = new Project();

  constructor(
    private projectService: ProjectService,
    public dialogRef: MatDialogRef<NewProjectComponent>,
    public userService: UserService,
    public ticketService: TicketService,
    @Inject(MAT_DIALOG_DATA) data: Project
  ) {
    this.project = data;
  }

  onDialogSave(title: string) {
    if (this.project.id == null || this.project.id == '') {
      let project = new Project();
      project.title = title;
      this.projectService.addProject(project).subscribe((result) => {
        if (result) {
          this.dialogRef.close();
        }
      });
    } else {
      this.projectService.updateProject(this.project).subscribe((result) => {
        if (result) {
          this.dialogRef.close();
        }
      });
    }
  }

  onDialogClose() {
    this.dialogRef.close();
  }



}
