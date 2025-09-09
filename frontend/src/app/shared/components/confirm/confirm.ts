import { Component, Inject } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import {
  MatDialogActions,
  MatDialogClose,
  MatDialogContent,
  MatDialogTitle,
  MAT_DIALOG_DATA,
} from '@angular/material/dialog';

@Component({
  selector: 'app-confirm',
  imports: [MatButtonModule, MatDialogActions, MatDialogClose, MatDialogTitle, MatDialogContent],
  templateUrl: './confirm.html',
  styleUrl: './confirm.scss',
})
export class Confirm {
  public title!: string;
  public question!: string;

  public CANCEL_BUTTON_ID: string = 'cancel-dialog';
  public OK_BUTTON_ID: string = 'ok-dialog';
  public CANCEL_BUTTON_LABEL: string = $localize`Cancel`;
  public OK_BUTTON_LABEL: string = $localize`Ok`;

  constructor(@Inject(MAT_DIALOG_DATA) data: any) {
    this.title = data.title;
    this.question = data.question;
  }
}
