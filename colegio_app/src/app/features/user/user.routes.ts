import { Routes } from '@angular/router';

export const userRoutes: Routes = [
  {
    path: '',
    loadComponent: () => import('./ui/user-list/user-list').then((m) => m.UserList),
  },
  {
    path: 'create',
    loadComponent: () => import('./ui/user-form/user-form').then((m) => m.UserForm),
  },
  {
    path: 'edit/:id',
    loadComponent: () => import('./ui/user-form/user-form').then((m) => m.UserForm),
  },
];
