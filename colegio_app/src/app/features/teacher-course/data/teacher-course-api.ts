import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { TeacherCourseRequest, TeacherCourseResponse } from './teacher-course.models';

const BASE = 'http://localhost:8080/api/teacher-courses';

@Injectable({ providedIn: 'root' })
export class TeacherCourseApi {
  private http = inject(HttpClient);

  getAll() {
    return this.http.get<TeacherCourseResponse[]>(BASE);
  }

  save(dto: TeacherCourseRequest) {
    return this.http.post<TeacherCourseResponse>(BASE, dto);
  }

  updateEvalNames(id: number, evalNames: string[]) {
    return this.http.patch<TeacherCourseResponse>(`${BASE}/${id}/eval-names`, { evalNames });
  }

  delete(id: number) {
    return this.http.delete<void>(`${BASE}/${id}`);
  }
}
