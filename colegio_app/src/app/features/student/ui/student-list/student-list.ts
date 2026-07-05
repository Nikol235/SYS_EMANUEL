import { Component, inject, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { TableModule } from 'primeng/table';
import { ButtonModule } from 'primeng/button';
import { TagModule } from 'primeng/tag';
import { ToastModule } from 'primeng/toast';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { MessageService, ConfirmationService } from 'primeng/api';
import { StudentApi } from '../../data/student-api';
import { StudentResponse } from '../../data/student.models';
import { AuthState } from '../../../../core/auth-state';

@Component({
  selector: 'app-student-list',
  standalone: true,
  imports: [CommonModule, TableModule, ButtonModule, TagModule, ToastModule, ConfirmDialogModule],
  providers: [MessageService, ConfirmationService],
  templateUrl: './student-list.html',
  styleUrl: './student-list.scss',
})
export class StudentList implements OnInit {
  private studentApi = inject(StudentApi);
  private messageService = inject(MessageService);
  private confirmationService = inject(ConfirmationService);
  private router = inject(Router);
  authState = inject(AuthState);

  $students = signal<StudentResponse[]>([]);
  $loading = signal(false);

  ngOnInit() {
    this.load();
  }

  load() {
    this.$loading.set(true);
    this.studentApi.getAll().subscribe({
      next: (data) => { this.$students.set(data); this.$loading.set(false); },
      error: () => { this.$loading.set(false); this.messageService.add({ severity: 'error', summary: 'Error', detail: 'No se pudo cargar alumnos' }); },
    });
  }

  create() { this.router.navigate(['/students/create']); }
  edit(s: StudentResponse) { this.router.navigate(['/students/edit', s.id]); }

  confirmDelete(s: StudentResponse) {
    this.confirmationService.confirm({
      message: `¿Eliminar a ${s.firstName} ${s.lastName}?`,
      header: 'Confirmar',
      icon: 'pi pi-trash',
      accept: () => this.delete(s),
    });
  }

  delete(s: StudentResponse) {
    this.studentApi.delete(s.id).subscribe({
      next: () => { this.messageService.add({ severity: 'success', summary: 'Eliminado', detail: 'Alumno eliminado' }); this.load(); },
      error: () => this.messageService.add({ severity: 'error', summary: 'Error', detail: 'No se pudo eliminar' }),
    });
  }
}
