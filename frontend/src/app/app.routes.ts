import { Routes } from '@angular/router';
import { TasksDashboard } from './modules/tasks/pages/tasks-dashboard/tasks-dashboard';
import { PageNotFound } from './core/pages/page-not-found/page-not-found';

export const routes: Routes = [
  { path: '', redirectTo: '/tasks', pathMatch: 'full' },
  {
    path: 'tasks',
    title: $localize`Tasks dashboard`,
    component: TasksDashboard,
  },
  { path: '**', title: $localize`Page not found`, component: PageNotFound },
];
