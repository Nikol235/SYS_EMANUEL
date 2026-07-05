import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { UserRequest, UserResponse } from './user.models';
import { environment } from '../../../../environments/environment';

const BASE = `${environment.apiUrl}/users`;

@Injectable({ providedIn: 'root' })
export class UserApi {
  private http = inject(HttpClient);

  getAll() {
    return this.http.get<UserResponse[]>(`${BASE}/all`);
  }

  getStaff() {
    return this.http.get<UserResponse[]>(`${BASE}/staff`);
  }

  getById(id: number) {
    return this.http.get<UserResponse>(`${BASE}/${id}`);
  }

  save(dto: UserRequest) {
    return this.http.post<UserResponse>(BASE, dto);
  }

  update(id: number, dto: UserRequest) {
    return this.http.put<UserResponse>(`${BASE}/${id}`, dto);
  }

  delete(id: number) {
    return this.http.delete<void>(`${BASE}/${id}`);
  }
}
