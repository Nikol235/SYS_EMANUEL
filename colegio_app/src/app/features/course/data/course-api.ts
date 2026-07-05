import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CourseRequest, CourseResponse } from './course.models';

const BASE = 'http://localhost:8080/api/courses';

@Injectable({ providedIn: 'root' })
export class CourseApi {
  private http = inject(HttpClient);

  getAll() {
    return this.http.get<CourseResponse[]>(BASE);
  }

  save(dto: CourseRequest) {
    return this.http.post<CourseResponse>(BASE, dto);
  }

  update(id: number, dto: CourseRequest) {
    return this.http.put<CourseResponse>(`${BASE}/${id}`, dto);
  }

  delete(id: number) {
    return this.http.delete<void>(`${BASE}/${id}`);
  }
}
