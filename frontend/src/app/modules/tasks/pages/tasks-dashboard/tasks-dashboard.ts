import { Component, inject } from '@angular/core';
import { TaskService } from '../../services/task-service';
import { Card } from '../../../../shared/components/card/card';
import { Icon } from '../../../../shared/components/icon/icon';
import { Button } from '../../../../shared/components/button/button';

@Component({
  selector: 'app-tasks-dashboard',
  imports: [Card, Icon, Button],
  templateUrl: './tasks-dashboard.html',
  styleUrl: './tasks-dashboard.scss',
})
export class TasksDashboard {
  public taskService: TaskService = inject(TaskService);

  public EDIT_BUTTON_ID_PREFIX: string = 'edit-task-';
  public DELETE_BUTTON_ID_PREFIX: string = 'delete-task-';
  public EDIT_BUTTON_LABEL: string = $localize`Edit task`;
  public DELETE_BUTTON_LABEL: string = $localize`Delete task`;

  public editTask(taskId?: number): void {}

  public deleteTask(taskId?: number): void {}
}
