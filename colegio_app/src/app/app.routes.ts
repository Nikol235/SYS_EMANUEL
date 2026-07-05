import { Routes } from '@angular/router';
import { authGuard } from './core/auth-guard';

export const routes: Routes = [
  {
    path: '',
    loadComponent: () => import('./layout/main-layout/main-layout').then((m) => m.MainLayout),
    canActivate: [authGuard],
    children: [
      {
        path: '',
        loadComponent: () =>
          import('./features/dashboard/ui/dashboard/dashboard').then((m) => m.Dashboard),
      },
      {
        path: 'students',
        loadChildren: () =>
          import('./features/student/student.routes').then((m) => m.studentRoutes),
      },
      {
        path: 'grade-levels',
        loadChildren: () =>
          import('./features/grade-level/grade-level.routes').then((m) => m.gradeLevelRoutes),
      },
      {
        path: 'courses',
        loadChildren: () =>
          import('./features/course/course.routes').then((m) => m.courseRoutes),
      },
      {
        path: 'teacher-courses',
        loadChildren: () =>
          import('./features/teacher-course/teacher-course.routes').then((m) => m.teacherCourseRoutes),
      },
      {
        path: 'attendance',
        loadChildren: () =>
          import('./features/attendance/attendance.routes').then((m) => m.attendanceRoutes),
      },
      {
        path: 'grades',
        loadChildren: () =>
          import('./features/grade/grade.routes').then((m) => m.gradeRoutes),
      },
      {
        path: 'payments',
        loadChildren: () =>
          import('./features/payment/payment.routes').then((m) => m.paymentRoutes),
      },
      {
        path: 'communications',
        loadChildren: () =>
          import('./features/communication/communication.routes').then((m) => m.communicationRoutes),
      },
      {
        path: 'schedules',
        loadChildren: () =>
          import('./features/schedule/schedule.routes').then((m) => m.scheduleRoutes),
      },
      {
        path: 'users',
        loadChildren: () =>
          import('./features/user/user.routes').then((m) => m.userRoutes),
      },
      {
        path: 'teachers',
        loadChildren: () =>
          import('./features/teacher/teacher.routes').then((m) => m.teacherRoutes),
      },
    ],
  },
  {
    path: 'auth',
    loadComponent: () => import('./layout/auth-layout/auth-layout').then((m) => m.AuthLayout),
    children: [
      {
        path: '',
        loadChildren: () => import('./features/auth/auth.routes').then((m) => m.authRoutes),
      },
    ],
  },
  {
    path: '**',
    redirectTo: '',
  },
];
