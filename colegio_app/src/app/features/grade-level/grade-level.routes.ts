import { Routes } from '@angular/router';

export const gradeLevelRoutes: Routes = [
  {
    path: '',
    loadComponent: () => import('./ui/grade-level-list/grade-level-list').then((m) => m.GradeLevelList),
  },
  {
    path: 'create',
    loadComponent: () => import('./ui/grade-level-form/grade-level-form').then((m) => m.GradeLevelForm),
  },
  {
    path: 'edit/:id',
    loadComponent: () => import('./ui/grade-level-form/grade-level-form').then((m) => m.GradeLevelForm),
  },
];
