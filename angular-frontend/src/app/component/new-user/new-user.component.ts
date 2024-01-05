import { Component, Inject, OnInit } from '@angular/core';
import { User } from '../../models/user';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { UserService } from '../../services/user.service';

@Component({
  selector: 'app-new-user',
  templateUrl: './new-user.component.html',
  styleUrl: './new-user.component.css',
})
export class NewUserComponent implements OnInit {
  user: User = new User();

  constructor(
    private userService: UserService,
    public dialogRef: MatDialogRef<NewUserComponent>,
    @Inject(MAT_DIALOG_DATA) data: User
  ) {
    this.user = data;
  }

  roleArrayList: string[] = ['Admin', 'Normal'];

  ngOnInit(): void {}

  onDialogSave(username: string, password: string, role: string): void {
    if (this.user.id == null || this.user.id == '') {
      let user: User = new User();
      user.username = username;
      user.password = password;
      user.role = role;
      this.userService.saveUser(user).subscribe((result) => {
        if (result) {
          this.dialogRef.close();
        }
      });
    } else {
      this.userService.updateUser(this.user).subscribe((result) => {
        if (result) {
          this.dialogRef.close();
        }
      });
    }
  }

  onDialogClose(): void {
    this.dialogRef.close();
  }
}
