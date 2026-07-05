import { Component, inject, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, ReactiveFormsModule } from '@angular/forms';
import { TableModule } from 'primeng/table';
import { ButtonModule } from 'primeng/button';
import { ToastModule } from 'primeng/toast';
import { SelectModule } from 'primeng/select';
import { DatePickerModule } from 'primeng/datepicker';
import { TagModule } from 'primeng/tag';
import { MessageService } from 'primeng/api';
import { AttendanceApi } from '../../data/attendance-api';
import { AttendanceResponse } from '../../data/attendance.models';
import { StudentApi } from '../../../student/data/student-api';
import { AuthState } from '../../../../core/auth-state';

@Component({
  selector: 'app-attendance-list',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, TableModule, ButtonModule, ToastModule, SelectModule, DatePickerModule, TagModule],
  providers: [MessageService],
  templateUrl: './attendance-list.html',
  styleUrl: './attendance-list.scss',
})
export class AttendanceList implements OnInit {
  private attendanceApi = inject(AttendanceApi);
  private studentApi = inject(StudentApi);
  private messageService = inject(MessageService);
  private fb = inject(FormBuilder);
  authState = inject(AuthState);

  $records = signal<AttendanceResponse[]>([]);
  $students = signal<any[]>([]);
  $loading = signal(false);

  filterForm = this.fb.group({
    studentId: [null as number | null],
    date: [null as Date | null],
  });

  ngOnInit() {
    this.studentApi.getAll().subscribe(s => this.$students.set(s));
    this.load();
  }

  load() {
    this.$loading.set(true);
    const v = this.filterForm.value;
    const studentId = v.studentId ?? undefined;
    const date = v.date ? (v.date as Date).toISOString().slice(0, 10) : undefined;
    this.attendanceApi.getAll(studentId, date).subscribe({
      next: (d) => { this.$records.set(d); this.$loading.set(false); },
      error: () => this.$loading.set(false),
    });
  }

  markBulkToday() {
    this.markBulk(new Date());
  }

  markBulk(date: Date) {
    const students = this.$students();
    if (!students.length) return;
    const records = students.map(s => ({
      studentId: s.id,
      date: date.toISOString().slice(0, 10),
      status: 'presente',
    }));
    this.attendanceApi.saveBulk(records).subscribe({
      next: () => { this.messageService.add({ severity: 'success', summary: 'Asistencia registrada', detail: `${records.length} registros guardados` }); this.load(); },
      error: () => this.messageService.add({ severity: 'error', summary: 'Error', detail: 'No se pudo registrar' }),
    });
  }

  getStatusSeverity(status: string): 'success' | 'warn' | 'danger' {
    return status === 'presente' ? 'success' : status === 'tardanza' ? 'warn' : 'danger';
  }

  canEditAttendance(): boolean {
    return !this.authState.$isPadre();
  }

  updateStatus(record: AttendanceResponse, status: string) {
    if (record.status === status) return;
    const previous = record.status;
    this.$records.update(list => list.map(r => r.id === record.id ? { ...r, status } : r));

    this.attendanceApi.save({
      studentId: record.student.id,
      date: record.date,
      status,
      turno: record.turno,
      tipo: record.tipo,
    }).subscribe({
      next: () => this.messageService.add({ severity: 'success', summary: 'Actualizado', detail: 'Asistencia actualizada' }),
      error: () => {
        this.$records.update(list => list.map(r => r.id === record.id ? { ...r, status: previous } : r));
        this.messageService.add({ severity: 'error', summary: 'Error', detail: 'No se pudo actualizar la asistencia' });
      },
    });
  }
}
