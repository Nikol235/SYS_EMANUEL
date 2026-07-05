import { Routes } from '@angular/router';

export const courseRoutes: Routes = [
  {
    path: '',
    loadComponent: () => import('./ui/course-list/course-list').then((m) => m.CourseList),
  },
];
