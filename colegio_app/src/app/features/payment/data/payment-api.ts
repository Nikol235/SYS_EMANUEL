import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { PaymentRequest, PaymentResponse } from './payment.models';

const BASE = 'http://localhost:8080/api/payments';

@Injectable({ providedIn: 'root' })
export class PaymentApi {
  private http = inject(HttpClient);

  getAll(studentId?: number) {
    let params = new HttpParams();
    if (studentId) params = params.set('studentId', studentId);
    return this.http.get<PaymentResponse[]>(BASE, { params });
  }

  create(dto: PaymentRequest) {
    return this.http.post<PaymentResponse>(BASE, dto);
  }

  update(id: number, dto: PaymentRequest) {
    return this.http.put<PaymentResponse>(`${BASE}/${id}`, dto);
  }
}
