import { Routes } from '@angular/router';

export const communicationRoutes: Routes = [
  {
    path: '',
    loadComponent: () => import('./ui/communication-list/communication-list').then((m) => m.CommunicationList),
  },
];
