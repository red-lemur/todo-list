import { Component, inject } from '@angular/core';
import { TaskService } from '../../services/task-service';
import { Card } from '../../../../shared/components/card/card';
import { Icon } from '../../../../shared/components/icon/icon';
import { Button } from '../../../../shared/components/button/button';
import { Confirm } from '../../../../shared/components/confirm/confirm';
import { MatDialog, MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-tasks-dashboard',
  imports: [Card, Icon, Button],
  templateUrl: './tasks-dashboard.html',
  styleUrl: './tasks-dashboard.scss',
})
export class TasksDashboard {
  readonly dialog: MatDialog = inject(MatDialog);

  public taskService: TaskService = inject(TaskService);

  public EDIT_BUTTON_ID_PREFIX: string = 'edit-task-';
  public DELETE_BUTTON_ID_PREFIX: string = 'delete-task-';
  public EDIT_BUTTON_LABEL: string = $localize`Edit task`;
  public DELETE_BUTTON_LABEL: string = $localize`Delete task`;

  public DELETE_DIALOG_TITLE: string = $localize`Delete a task`;
  public DELETE_DIALOG_QUESTION: string = $localize`Do you really want to delete this task?`;

  constructor() {
    this.taskService.getAllTasks();
  }

  public editTask(taskId?: number): void {}

  public deleteTask(taskId: number): void {
    const dialogRef: MatDialogRef<Confirm> = this.dialog.open(Confirm, {
      data: { title: this.DELETE_DIALOG_TITLE, question: this.DELETE_DIALOG_QUESTION },
    });

    dialogRef.afterClosed().subscribe((confirmed: boolean) => {
      if (confirmed) {
        this.taskService.deleteTask(taskId).subscribe({
          next: () => {
            this.taskService.getAllTasks();
          },
          error: () => {},
        });
      }
    });
  }
}
