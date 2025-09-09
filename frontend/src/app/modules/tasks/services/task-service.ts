import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { catchError, of } from 'rxjs';
import { toSignal } from '@angular/core/rxjs-interop';
import { Task } from '../models/task';
import { environment } from '../../../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class TaskService {
  private httpClient: HttpClient = inject(HttpClient);

  tasks = toSignal(
    this.httpClient.get<Task[]>(`${environment.apiUrl}`).pipe(
      catchError(() => of([])) // En cas d'erreur, retourne un tableau vide
    ),
    { initialValue: [] } // Valeur par défaut avant la réponse
  );
}
