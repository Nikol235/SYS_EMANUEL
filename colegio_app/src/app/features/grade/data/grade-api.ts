import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { GradeRequest, GradeResponse } from './grade.models';
import { environment } from '../../../../environments/environment';

const BASE = `${environment.apiUrl}/grades`;

@Injectable({ providedIn: 'root' })
export class GradeApi {
  private http = inject(HttpClient);

  getAll(studentId?: number, teacherCourseId?: number) {
    let params = new HttpParams();
    if (studentId) params = params.set('studentId', studentId);
    if (teacherCourseId) params = params.set('teacherCourseId', teacherCourseId);
    return this.http.get<GradeResponse[]>(BASE, { params });
  }

  save(dto: GradeRequest) {
    return this.http.post<GradeResponse>(BASE, dto);
  }

  update(id: number, score: number) {
    return this.http.put<GradeResponse>(`${BASE}/${id}`, { score });
  }

  delete(id: number) {
    return this.http.delete<void>(`${BASE}/${id}`);
  }
}
