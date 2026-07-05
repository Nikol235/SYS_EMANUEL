import { Component, inject, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { TableModule } from 'primeng/table';
import { ButtonModule } from 'primeng/button';
import { ToastModule } from 'primeng/toast';
import { DialogModule } from 'primeng/dialog';
import { InputText } from 'primeng/inputtext';
import { SelectModule } from 'primeng/select';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { MessageService, ConfirmationService } from 'primeng/api';
import { ScheduleApi } from '../../data/schedule-api';
import { ScheduleResponse } from '../../data/schedule.models';
import { GradeLevelApi } from '../../../grade-level/data/grade-level-api';
import { AuthState } from '../../../../core/auth-state';

@Component({
  selector: 'app-schedule-list',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, TableModule, ButtonModule, ToastModule, DialogModule, InputText, SelectModule, ConfirmDialogModule],
  providers: [MessageService, ConfirmationService],
  templateUrl: './schedule-list.html',
  styleUrl: './schedule-list.scss',
})
export class ScheduleList implements OnInit {
  private scheduleApi = inject(ScheduleApi);
  private gradeLevelApi = inject(GradeLevelApi);
  private messageService = inject(MessageService);
  private confirmationService = inject(ConfirmationService);
  private fb = inject(FormBuilder);
  authState = inject(AuthState);

  $items = signal<ScheduleResponse[]>([]);
  $gradeLevels = signal<any[]>([]);
  $loading = signal(false);
  dialogVisible = false;
  $selectedGradeLevel = signal<number | null>(null);

  dayOptions = [
    { label: 'Lunes', value: 1 }, { label: 'Martes', value: 2 },
    { label: 'Miércoles', value: 3 }, { label: 'Jueves', value: 4 },
    { label: 'Viernes', value: 5 },
  ];

  form = this.fb.group({
    gradeLevelId: [null as number | null, Validators.required],
    dayOfWeek: [null as number | null, Validators.required],
    startTime: ['', Validators.required],
    endTime: ['', Validators.required],
    subject: ['', Validators.required],
    color: ['#3B82F6'],
  });

  ngOnInit() {
    this.gradeLevelApi.getAll().subscribe(g => this.$gradeLevels.set(g));
    this.load();
  }

  load() {
    this.$loading.set(true);
    this.scheduleApi.getAll(this.$selectedGradeLevel() ?? undefined).subscribe({
      next: (d) => { this.$items.set(d); this.$loading.set(false); },
      error: () => this.$loading.set(false),
    });
  }

  getDayName(n: number): string {
    return this.dayOptions.find(d => d.value === n)?.label ?? String(n);
  }

  submit() {
    if (this.form.invalid) return;
    this.scheduleApi.save(this.form.value as any).subscribe({
      next: () => { this.messageService.add({ severity: 'success', summary: 'Guardado', detail: 'Horario guardado' }); this.dialogVisible = false; this.load(); },
      error: () => this.messageService.add({ severity: 'error', summary: 'Error', detail: 'No se pudo guardar' }),
    });
  }

  confirmDelete(s: ScheduleResponse) {
    this.confirmationService.confirm({
      message: `¿Eliminar ${s.subject} del ${this.getDayName(s.dayOfWeek)}?`,
      header: 'Confirmar',
      icon: 'pi pi-trash',
      accept: () => {
        this.scheduleApi.delete(s.id).subscribe({
          next: () => { this.messageService.add({ severity: 'success', summary: 'Eliminado', detail: 'Horario eliminado' }); this.load(); },
          error: () => this.messageService.add({ severity: 'error', summary: 'Error', detail: 'No se pudo eliminar' }),
        });
      },
    });
  }
}
