import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { CommunicationPage, CommunicationRequest, CommunicationResponse } from './communication.models';

const BASE = 'http://localhost:8080/api/communications';

@Injectable({ providedIn: 'root' })
export class CommunicationApi {
  private http = inject(HttpClient);

  getAll(page = 0, size = 20) {
    const params = new HttpParams().set('page', page).set('size', size);
    return this.http.get<CommunicationPage>(BASE, { params });
  }

  save(dto: CommunicationRequest) {
    return this.http.post<CommunicationResponse>(BASE, dto);
  }

  update(id: number, dto: CommunicationRequest) {
    return this.http.put<CommunicationResponse>(`${BASE}/${id}`, dto);
  }

  delete(id: number) {
    return this.http.delete<void>(`${BASE}/${id}`);
  }
}
