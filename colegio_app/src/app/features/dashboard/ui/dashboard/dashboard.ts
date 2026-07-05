import { Component, inject, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CardModule } from 'primeng/card';
import { HttpClient } from '@angular/common/http';
import { AuthState } from '../../../../core/auth-state';
import { environment } from '../../../../../environments/environment';

const BASE = `${environment.apiUrl}/dashboard`;

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, CardModule],
  templateUrl: './dashboard.html',
  styleUrl: './dashboard.scss',
})
export class Dashboard implements OnInit {
  authState = inject(AuthState);
  private http = inject(HttpClient);

  $data = signal<any>(null);

  ngOnInit() {
    const role = this.authState.$role();
    if (role === 'admin' || role === 'director' || role === 'secretaria' || role === 'auxiliar') {
      this.http.get(`${BASE}/admin`).subscribe(d => this.$data.set(d));
    } else if (role === 'docente') {
      this.http.get(`${BASE}/docente`).subscribe(d => this.$data.set(d));
    } else if (role === 'padre') {
      this.http.get(`${BASE}/padre`).subscribe(d => this.$data.set(d));
    }
  }
}
