import { Routes } from '@angular/router';

export const teacherCourseRoutes: Routes = [
  {
    path: '',
    loadComponent: () => import('./ui/teacher-course-list/teacher-course-list').then((m) => m.TeacherCourseList),
  },
];
