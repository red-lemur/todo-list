import { Component, Inject, signal, WritableSignal } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import {
  MatDialogActions,
  MatDialogClose,
  MatDialogTitle,
  MatDialogContent,
  MAT_DIALOG_DATA,
} from '@angular/material/dialog';
import { Task } from '../../models/task';
import { FormControl, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { merge } from 'rxjs';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { MatCheckboxModule } from '@angular/material/checkbox';

@Component({
  selector: 'app-create-or-update-task-dialog',
  imports: [
    MatButtonModule,
    MatDialogActions,
    MatDialogClose,
    MatDialogTitle,
    MatDialogContent,
    MatFormFieldModule,
    MatInputModule,
    FormsModule,
    ReactiveFormsModule,
    MatCheckboxModule,
  ],
  templateUrl: './create-or-update-task-dialog.html',
  styleUrl: './create-or-update-task-dialog.scss',
})
export class CreateOrUpdateTaskDialog {
  public taskDetails!: Task;
  public dialogTitle!: string;

  /* Boutons */
  public CANCEL_BUTTON_ID: string = 'cancel-edit';
  public SAVE_BUTTON_ID: string = 'ok-edit';
  public CANCEL_BUTTON_LABEL: string = $localize`Cancel`;
  public SAVE_BUTTON_LABEL: string = $localize`Save`;

  /* Labels du formulaire */
  public TASK_LABEL_INPUT_LABEL: string = $localize`Task label`;
  public TASK_DESCRIPTION_INPUT_LABEL: string = $localize`Task description`;
  public TASK_COMPLETED_CHECKBOX_LABEL: string = $localize`Task is completed`;

  // Nombre de lignes d'affichage du champ description par défaut
  public TASK_DESCRIPTION_ROWS_NB: number = 5;

  /* FormControls */
  public taskLabelControl: FormControl = new FormControl('', [Validators.required]);
  public taskDescriptionControl: FormControl = new FormControl('');
  public taskCompletedControl: FormControl = new FormControl(false);

  /* Validation du formulaire */
  public taskLabelErrorMessage: WritableSignal<string> = signal('');
  private TASK_LABEL_ERROR_MESSAGE_REQUIRED: string = $localize`You must enter a task label`;

  constructor(@Inject(MAT_DIALOG_DATA) data: any) {
    this.taskDetails = data.taskDetails;
    this.dialogTitle = data.dialogTitle;

    merge(this.taskLabelControl.statusChanges, this.taskLabelControl.valueChanges)
      .pipe(takeUntilDestroyed())
      .subscribe(() => this.updateTaskLabelErrorMessage());

    this.setControls();
  }

  private setControls(): void {
    this.taskLabelControl.setValue(this.taskDetails.label);
    this.taskDescriptionControl.setValue(this.taskDetails.description);
    this.taskCompletedControl.setValue(this.taskDetails.completed);
  }

  public updateTaskLabelErrorMessage(): void {
    if (this.taskLabelControl.hasError('required')) {
      this.taskLabelErrorMessage.set(this.TASK_LABEL_ERROR_MESSAGE_REQUIRED);
    } else {
      this.taskLabelErrorMessage.set('');
    }
  }

  public getUpdatedTaskDetails(): Task | undefined {
    if (this.taskLabelControl.invalid) {
      // On a essayé de forcer le bouton
      return undefined;
    }
    return new Task(
      this.taskDetails.id,
      this.taskLabelControl.value,
      this.taskDescriptionControl.value,
      this.taskCompletedControl.value
    );
  }
}
