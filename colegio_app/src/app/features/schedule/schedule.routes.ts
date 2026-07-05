import { Routes } from '@angular/router';

export const scheduleRoutes: Routes = [
  {
    path: '',
    loadComponent: () => import('./ui/schedule-list/schedule-list').then((m) => m.ScheduleList),
  },
];
