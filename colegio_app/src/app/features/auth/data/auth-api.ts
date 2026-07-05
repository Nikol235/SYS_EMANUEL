import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { AuthRequest, AuthResponse } from './auth.models';
import { environment } from '../../../../environments/environment';

const BASE = `${environment.apiUrl}/auth`;

@Injectable({ providedIn: 'root' })
export class AuthApi {
  private http = inject(HttpClient);

  login(dto: AuthRequest) {
    return this.http.post<AuthResponse>(`${BASE}/login`, dto);
  }

  me() {
    return this.http.get<any>(`${BASE}/me`);
  }
}
