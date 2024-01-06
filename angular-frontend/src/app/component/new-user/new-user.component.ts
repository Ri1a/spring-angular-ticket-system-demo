import { Component, Inject, OnInit } from '@angular/core';
import { User } from '../../models/user';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { UserService } from '../../services/user.service';
import { FormGroup, FormControl, Validators } from '@angular/forms';

@Component({
  selector: 'app-new-user',
  templateUrl: './new-user.component.html',
  styleUrl: './new-user.component.css',
})
export class NewUserComponent implements OnInit {
  userForm: FormGroup;
  user: User = new User();
  roleArrayList: string[] = ['ROLE_ADMIN', 'ROLE_USER'];

  constructor(
    private userService: UserService,
    public dialogRef: MatDialogRef<NewUserComponent>,
    @Inject(MAT_DIALOG_DATA) data: User
  ) {
    this.user = data;
    this.userForm = new FormGroup({
      username: new FormControl(this.user.username, [Validators.required]),
      password: new FormControl(this.user.password, [
        Validators.required,
        Validators.minLength(6),
      ]),
      role: new FormControl('ROLE_USER'),
    });
  }

  ngOnInit(): void {}

  onDialogSave(): void {
    if (this.userForm.valid) {
      const formValue = this.userForm.value;
      this.user.username = formValue.username;
      this.user.password = formValue.password;
      this.user.authorities = [formValue.role];

      if (!this.user.id) {
        this.userService.saveUser(this.user).subscribe((result) => {
          this.dialogRef.close();
        });
      } else {
        this.userService.updateUser(this.user).subscribe((result) => {
          this.dialogRef.close();
        });
      }
    }
  }

  onDialogClose(): void {
    this.dialogRef.close();
  }
}
