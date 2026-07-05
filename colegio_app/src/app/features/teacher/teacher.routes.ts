import { Routes } from '@angular/router';

export const teacherRoutes: Routes = [
  {
    path: '',
    loadComponent: () => import('./ui/teacher-list/teacher-list').then((m) => m.TeacherList),
  },
  {
    path: 'create',
    loadComponent: () => import('./ui/teacher-form/teacher-form').then((m) => m.TeacherForm),
  },
  {
    path: 'edit/:id',
    loadComponent: () => import('./ui/teacher-form/teacher-form').then((m) => m.TeacherForm),
  },
];
