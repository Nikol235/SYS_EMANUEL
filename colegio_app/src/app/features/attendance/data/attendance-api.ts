import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { AttendanceRequest, AttendanceResponse } from './attendance.models';

const BASE = 'http://localhost:8080/api/attendance';

@Injectable({ providedIn: 'root' })
export class AttendanceApi {
  private http = inject(HttpClient);

  getAll(studentId?: number, date?: string) {
    let params = new HttpParams();
    if (studentId) params = params.set('studentId', studentId);
    if (date) params = params.set('date', date);
    return this.http.get<AttendanceResponse[]>(BASE, { params });
  }

  save(dto: AttendanceRequest) {
    return this.http.post<AttendanceResponse>(BASE, dto);
  }

  saveBulk(records: AttendanceRequest[]) {
    return this.http.post<AttendanceResponse[]>(`${BASE}/bulk`, records);
  }

  delete(studentId: number, date: string, turno?: string, tipo?: string) {
    let params = new HttpParams().set('studentId', studentId).set('date', date);
    if (turno) params = params.set('turno', turno);
    if (tipo) params = params.set('tipo', tipo);
    return this.http.delete<void>(BASE, { params });
  }
}
