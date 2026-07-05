import { Routes } from '@angular/router';

export const gradeRoutes: Routes = [
  {
    path: '',
    loadComponent: () => import('./ui/grade-list/grade-list').then((m) => m.GradeList),
  },
];
