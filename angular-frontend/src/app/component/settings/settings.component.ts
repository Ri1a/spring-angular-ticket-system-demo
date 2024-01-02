import { Component, OnInit } from '@angular/core';
import {User} from "../../models/user";
import {MatDialog} from "@angular/material/dialog";
import {UserService} from "../../services/user.service";
import {NewUserComponent} from "../new-user/new-user.component";

@Component({
  selector: 'app-settings',
  templateUrl: './settings.component.html',
  styleUrl: './settings.component.css'
})
export class SettingsComponent implements OnInit {

  userRoleAdmin: User[] = [];
  userRoleNormal: User[] = [];

  constructor(private userService: UserService,
              public dialog: MatDialog) {
  }

  ngOnInit(): void {
    this.loadAllUser();
  }

  loadAllUser(): void {
    this.userService.getAllUser().subscribe(result => {
      this.userRoleAdmin = result.filter(x => x.role === "Admin")
      this.userRoleNormal = result.filter(x => x.role === "Normal")
    });
  }

  openDialogNewUser(): void {
    const dialogRef = this.dialog.open(NewUserComponent, {
      width: '500px',
      height: '450px',
      panelClass: 'my-dialog',
      data: new User(),
    });

    dialogRef.afterClosed().subscribe(
      () =>   this.loadAllUser()
    );
  }

  deleteUser(user_id: string): void {
    this.userService.deleteUser(user_id).subscribe(result => {
      this.loadAllUser();
    })
  }

  openDialogEdit(user: User): void {
    const dialogRef = this.dialog.open(NewUserComponent, {
      width: '500px',
      height: '450px',
      panelClass: 'my-dialog',
      data: user
    });
    dialogRef.afterClosed().subscribe(
      () =>   this.loadAllUser()
    );
  }
}
