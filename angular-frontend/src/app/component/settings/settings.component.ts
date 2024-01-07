import { Component, OnInit } from '@angular/core';
import { User } from '../../models/user';
import { MatDialog } from '@angular/material/dialog';
import { UserService } from '../../services/user.service';
import { NewUserComponent } from '../new-user/new-user.component';
import { BreakpointObserver, Breakpoints } from '@angular/cdk/layout';

@Component({
  selector: 'app-settings',
  templateUrl: './settings.component.html',
  styleUrl: './settings.component.css',
})
export class SettingsComponent implements OnInit {
  userRoleAdmin: User[] = [];
  userRoleNormal: User[] = [];

  isSmallScreen: boolean = false;

  constructor(private userService: UserService, public dialog: MatDialog,
              private breakpointObserver: BreakpointObserver) {}

  ngOnInit(): void {
    this.breakpointObserver.observe([
      Breakpoints.XSmall
    ]).subscribe(result => {
      this.isSmallScreen = result.matches;
    });
    this.loadAllUser();
  }

  loadAllUser(): void {
    this.userService.getAllUser().subscribe((result) => {
      this.userRoleAdmin = result.filter((user) =>
        user.authorities.some((auth) => auth.authority === 'ROLE_ADMIN')
      );
      this.userRoleNormal = result.filter((user) =>
        user.authorities.some((auth) => auth.authority === 'ROLE_USER')
      );
    });
  }

  getRole(user: User): string {
    return user.authorities.some((auth) => auth.authority === 'ROLE_ADMIN')
      ? 'Admin'
      : 'User';
  }

  openDialogNewUser(): void {
    const dialogRef = this.dialog.open(NewUserComponent, {
      width: '500px',
      height: '450px',
      panelClass: 'my-dialog',
      data: new User(),
    });

    dialogRef.afterClosed().subscribe(() => this.loadAllUser());
  }

  deleteUser(user_id: string): void {
    this.userService.deleteUser(user_id).subscribe((result) => {
      this.loadAllUser();
    });
  }

  openDialogEdit(user: User): void {
    const dialogRef = this.dialog.open(NewUserComponent, {
      width: '500px',
      height: '450px',
      panelClass: 'my-dialog',
      data: user,
    });
    dialogRef.afterClosed().subscribe(() => this.loadAllUser());
  }
}
