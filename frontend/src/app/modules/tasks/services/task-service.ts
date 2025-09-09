import { HttpClient } from '@angular/common/http';
import { inject, Injectable, Signal } from '@angular/core';
import { BehaviorSubject, catchError, Observable, of } from 'rxjs';
import { toSignal } from '@angular/core/rxjs-interop';
import { Task } from '../models/task';
import { environment } from '../../../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class TaskService {
  private httpClient: HttpClient = inject(HttpClient);

  public tasksSubject = new BehaviorSubject<Task[]>([]);
  public getAllTasks() {
    this.httpClient.get<Task[]>(`${environment.apiUrl}`).subscribe({
      next: (tasks: Task[]) => {
        this.tasksSubject.next(tasks);
      },
      error: () => {},
    });
  }
  public tasks = toSignal(this.tasksSubject);

  public deleteTask(taskId: number): Observable<void> {
    return this.httpClient.delete<void>(`${environment.apiUrl}/${taskId}`);
  }
}
