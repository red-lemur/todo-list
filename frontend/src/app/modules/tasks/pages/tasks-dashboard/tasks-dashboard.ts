import { Component, effect, inject, signal, Signal, WritableSignal } from '@angular/core';
import { TaskService } from '../../services/task-service';
import { Card } from '../../../../shared/components/card/card';
import { Icon } from '../../../../shared/components/icon/icon';
import { Button } from '../../../../shared/components/button/button';
import { Confirm } from '../../../../shared/components/confirm/confirm';
import { MatDialog, MatDialogRef } from '@angular/material/dialog';
import { Task } from '../../models/task';
import { CreateOrUpdateTaskDialog } from '../../components/create-or-update-task-dialog/create-or-update-task-dialog';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { FloatingButton } from '../../../../shared/components/floating-button/floating-button';

@Component({
  selector: 'app-tasks-dashboard',
  imports: [Card, Icon, Button, MatIconModule, MatButtonModule, FloatingButton],
  templateUrl: './tasks-dashboard.html',
  styleUrl: './tasks-dashboard.scss',
})
export class TasksDashboard {
  private dialog: MatDialog = inject(MatDialog);
  private taskService: TaskService = inject(TaskService);

  /* Liste des tâches affichées */
  public currentlyShowedTasks!: Signal<Task[] | undefined>;
  public tasksAreFiltered: WritableSignal<boolean> = signal(false);

  /* Icônes */
  public COMPLETED_ICON_ID_PREFIX: string = 'task-completed-';
  public COMPLETED_ICON_TITLE: string = $localize`Task completed`;
  public FILTER_BUTTON_ICON_NO_FILTER: string = 'filter_alt';
  public FILTER_BUTTON_ICON_FILTERED: string = 'filter_alt_off';
  public filterButtonIcon: WritableSignal<string> = signal(this.FILTER_BUTTON_ICON_NO_FILTER);

  /* Boutons */
  public FILTER_BUTTON_ID: string = 'filter-tasks';
  public CREATE_BUTTON_ID: string = 'create-task';
  public UPDATE_BUTTON_ID_PREFIX: string = 'update-task-';
  public DELETE_BUTTON_ID_PREFIX: string = 'delete-task-';
  public FILTER_BUTTON_LABEL_NO_FILTER: string = $localize`Show only non completed tasks`;
  public FILTER_BUTTON_LABEL_FILTERED: string = $localize`Show all tasks`;
  public filterButtonLabel: WritableSignal<string> = signal(this.FILTER_BUTTON_LABEL_NO_FILTER);
  public CREATE_BUTTON_LABEL: string = $localize`Create a task`;
  public UPDATE_BUTTON_LABEL: string = $localize`Update task`;
  public DELETE_BUTTON_LABEL: string = $localize`Delete task`;

  /* Dialogs */
  public CREATE_OR_UPDATE_DIALOG_HEIGHT: string = '30rem';
  public CREATE_OR_UPDATE_DIALOG_WIDTH: string = '50rem';
  public CREATE_DIALOG_TITLE: string = $localize`Create a task`;
  public UPDATE_DIALOG_TITLE: string = $localize`Update a task`;
  public DELETE_DIALOG_TITLE: string = $localize`Delete a task`;
  public DELETE_DIALOG_QUESTION: string = $localize`Do you really want to delete this task?`;

  constructor() {
    effect(() => {
      if (this.tasksAreFiltered()) {
        this.currentlyShowedTasks = this.taskService.nonCompletedTasks;
        this.filterButtonLabel.set(this.FILTER_BUTTON_LABEL_FILTERED);
        this.filterButtonIcon.set(this.FILTER_BUTTON_ICON_FILTERED);
      } else {
        this.currentlyShowedTasks = this.taskService.allTasks;
        this.filterButtonLabel.set(this.FILTER_BUTTON_LABEL_NO_FILTER);
        this.filterButtonIcon.set(this.FILTER_BUTTON_ICON_NO_FILTER);
      }
    });
    this.reloadTasks();
  }

  public onFilterTasksClicked(): void {
    this.tasksAreFiltered.set(!this.tasksAreFiltered());
    this.reloadTasks();
  }

  public onCreateTaskClicked(): void {
    const dialogRef: MatDialogRef<CreateOrUpdateTaskDialog> = this.openCreateOrUpdateTaskDialog(
      new Task(),
      this.CREATE_DIALOG_TITLE
    );
    dialogRef.afterClosed().subscribe((task?: Task) => {
      if (task) {
        this.createTaskAndReload(task);
      }
    });
  }

  public onUpdateTaskClicked(taskId: number): void {
    this.taskService.getTask(taskId).subscribe({
      next: (task: Task) => {
        const dialogRef: MatDialogRef<CreateOrUpdateTaskDialog> = this.openCreateOrUpdateTaskDialog(
          task,
          this.UPDATE_DIALOG_TITLE
        );
        dialogRef.afterClosed().subscribe((taskDetails?: Task) => {
          if (taskDetails) {
            this.updateTaskAndReload(taskDetails.id, taskDetails);
          }
        });
      },
      error: () => {},
    });
  }

  private openCreateOrUpdateTaskDialog(
    taskDetails: Task,
    dialogTitle: string
  ): MatDialogRef<CreateOrUpdateTaskDialog> {
    return this.dialog.open(CreateOrUpdateTaskDialog, {
      height: this.CREATE_OR_UPDATE_DIALOG_HEIGHT,
      width: this.CREATE_OR_UPDATE_DIALOG_WIDTH,
      data: { taskDetails: taskDetails, dialogTitle: dialogTitle },
    });
  }

  private createTaskAndReload(task: Task): void {
    this.taskService.createTask(task).subscribe({
      next: () => {
        this.reloadTasks();
      },
      error: () => {},
    });
  }

  private updateTaskAndReload(taskId: number, taskDetails: Task): void {
    this.taskService.updateTask(taskId, taskDetails).subscribe({
      next: () => {
        this.reloadTasks();
      },
      error: () => {},
    });
  }

  public onDeleteTaskClicked(taskId: number): void {
    const dialogRef: MatDialogRef<Confirm> = this.dialog.open(Confirm, {
      data: { title: this.DELETE_DIALOG_TITLE, question: this.DELETE_DIALOG_QUESTION },
    });
    dialogRef.afterClosed().subscribe((confirmed: boolean) => {
      if (confirmed) {
        this.deleteTaskAndReload(taskId);
      }
    });
  }

  private deleteTaskAndReload(taskId: number): void {
    this.taskService.deleteTask(taskId).subscribe({
      next: () => {
        this.reloadTasks();
      },
      error: () => {},
    });
  }

  private reloadTasks(): void {
    this.tasksAreFiltered()
      ? this.taskService.getNonCompletedTasks()
      : this.taskService.getAllTasks();
  }
}
