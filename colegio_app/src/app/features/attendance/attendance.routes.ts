import { Routes } from '@angular/router';

export const attendanceRoutes: Routes = [
  {
    path: '',
    loadComponent: () => import('./ui/attendance-list/attendance-list').then((m) => m.AttendanceList),
  },
];
