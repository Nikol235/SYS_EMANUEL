import { Routes } from '@angular/router';

export const paymentRoutes: Routes = [
  {
    path: '',
    loadComponent: () => import('./ui/payment-list/payment-list').then((m) => m.PaymentList),
  },
];
