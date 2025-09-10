import { HttpClient } from '@angular/common/http';
import { inject, Injectable, Signal } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { toSignal } from '@angular/core/rxjs-interop';
import { Task } from '../models/task';
import { environment } from '../../../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class TaskService {
  private httpClient: HttpClient = inject(HttpClient);

  public allTasksSubject: BehaviorSubject<Task[]> = new BehaviorSubject<Task[]>([]);
  public getAllTasks() {
    this.httpClient.get<Task[]>(`${environment.apiUrl}`).subscribe({
      next: (tasks: Task[]) => {
        this.allTasksSubject.next(tasks);
      },
      error: () => {},
    });
  }
  public allTasks: Signal<Task[] | undefined> = toSignal(this.allTasksSubject);

  public nonCompletedTasksSubject: BehaviorSubject<Task[]> = new BehaviorSubject<Task[]>([]);
  public getNonCompletedTasks() {
    this.httpClient.get<Task[]>(`${environment.apiUrl}/completed?completed=false`).subscribe({
      next: (tasks: Task[]) => {
        this.nonCompletedTasksSubject.next(tasks);
      },
      error: () => {},
    });
  }
  public nonCompletedTasks: Signal<Task[] | undefined> = toSignal(this.nonCompletedTasksSubject);

  public getTask(taskId: number): Observable<Task> {
    return this.httpClient.get<Task>(`${environment.apiUrl}/${taskId}`);
  }

  public createTask(task: Task): Observable<void> {
    return this.httpClient.post<void>(`${environment.apiUrl}`, task);
  }

  public updateTask(taskId: number, taskDetails: Task): Observable<void> {
    return this.httpClient.put<void>(`${environment.apiUrl}/${taskId}`, taskDetails);
  }

  public deleteTask(taskId: number): Observable<void> {
    return this.httpClient.delete<void>(`${environment.apiUrl}/${taskId}`);
  }
}
