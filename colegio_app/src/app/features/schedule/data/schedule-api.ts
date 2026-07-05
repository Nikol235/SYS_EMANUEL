import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { ScheduleRequest, ScheduleResponse } from './schedule.models';
import { environment } from '../../../../environments/environment';

const BASE = `${environment.apiUrl}/schedules`;

@Injectable({ providedIn: 'root' })
export class ScheduleApi {
  private http = inject(HttpClient);

  getAll(gradeLevelId?: number) {
    let params = new HttpParams();
    if (gradeLevelId) params = params.set('gradeLevelId', gradeLevelId);
    return this.http.get<ScheduleResponse[]>(BASE, { params });
  }

  save(dto: ScheduleRequest) {
    return this.http.post<ScheduleResponse>(BASE, dto);
  }

  update(id: number, dto: Partial<ScheduleRequest>) {
    return this.http.put<ScheduleResponse>(`${BASE}/${id}`, dto);
  }

  delete(id: number) {
    return this.http.delete<void>(`${BASE}/${id}`);
  }
}
