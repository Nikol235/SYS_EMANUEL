import { Routes } from '@angular/router';

export const studentRoutes: Routes = [
  {
    path: '',
    loadComponent: () => import('./ui/student-list/student-list').then((m) => m.StudentList),
  },
  {
    path: 'create',
    loadComponent: () => import('./ui/student-form/student-form').then((m) => m.StudentForm),
  },
  {
    path: 'edit/:id',
    loadComponent: () => import('./ui/student-form/student-form').then((m) => m.StudentForm),
  },
];
