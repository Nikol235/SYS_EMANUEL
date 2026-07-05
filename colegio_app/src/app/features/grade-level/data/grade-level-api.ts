import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { GradeLevelRequest, GradeLevelResponse } from './grade-level.models';

const BASE = 'http://localhost:8080/api/grade-levels';

@Injectable({ providedIn: 'root' })
export class GradeLevelApi {
  private http = inject(HttpClient);

  getAll() {
    return this.http.get<GradeLevelResponse[]>(BASE);
  }

  getById(id: number) {
    return this.http.get<GradeLevelResponse>(`${BASE}/${id}`);
  }

  save(dto: GradeLevelRequest) {
    return this.http.post<GradeLevelResponse>(BASE, dto);
  }

  update(id: number, dto: Partial<GradeLevelRequest>) {
    return this.http.put<GradeLevelResponse>(`${BASE}/${id}`, dto);
  }

  delete(id: number) {
    return this.http.delete<void>(`${BASE}/${id}`);
  }

  getMembers(id: number) {
    return this.http.get<any[]>(`${BASE}/${id}/members`);
  }
}
