import {Component, OnInit} from '@angular/core';
import {MatDialog} from "@angular/material/dialog";
import {NewProjectComponent} from "../new-project/new-project.component";
import {Project} from "../../../models/project";
import {ProjectService} from "../../../services/project.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-project',
  templateUrl: './project.component.html',
  styleUrl: './project.component.css'
})
export class ProjectComponent implements OnInit {

  projectList: Project[] = [];

  constructor(private projectService: ProjectService, public dialog: MatDialog, private router: Router) {
  }

  ngOnInit(): void {
    this.loadAllProjects();
  }

  openDialogNewProject() {
    const dialogRef = this.dialog.open(NewProjectComponent, {
      width: '500px',
      height: '450px',
      panelClass: 'my-dialog',
      data: new Project(),
    });

    dialogRef.afterClosed().subscribe(
      () =>   this.loadAllProjects()
    );
  }

  openDialogEdit(project: Project) {
    const dialogRef = this.dialog.open(NewProjectComponent, {
      width: '500px',
      height: '450px',
      panelClass: 'my-dialog',
      data: project
    });

    dialogRef.afterClosed().subscribe(
      () =>   this.loadAllProjects()
    );
  }

  loadAllProjects(): void {
    this.projectService.getAllProjects().subscribe(result => {
      this.projectList = result;
    });
  }

  showTicketOverview(project: Project): void {
    this.router.navigate(['/overview', project.id])

  }

  deleteProject(projectId: string) {
    this.projectService.deleteProject(projectId).subscribe(result => {
      this.loadAllProjects();
    })
  }

}
