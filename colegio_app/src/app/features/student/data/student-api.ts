import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { StudentRequest, StudentResponse } from './student.models';

const BASE = 'http://localhost:8080/api/students';

@Injectable({ providedIn: 'root' })
export class StudentApi {
  private http = inject(HttpClient);

  getAll() {
    return this.http.get<StudentResponse[]>(BASE);
  }

  getById(id: number) {
    return this.http.get<StudentResponse>(`${BASE}/${id}`);
  }

  save(dto: StudentRequest) {
    return this.http.post<StudentResponse>(BASE, dto);
  }

  update(id: number, dto: Partial<StudentRequest>) {
    return this.http.put<StudentResponse>(`${BASE}/${id}`, dto);
  }

  delete(id: number) {
    return this.http.delete<void>(`${BASE}/${id}`);
  }
}
