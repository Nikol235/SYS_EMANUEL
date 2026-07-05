import { Component, inject, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { TableModule } from 'primeng/table';
import { ButtonModule } from 'primeng/button';
import { ToastModule } from 'primeng/toast';
import { DialogModule } from 'primeng/dialog';
import { InputText } from 'primeng/inputtext';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { MessageService, ConfirmationService } from 'primeng/api';
import { CourseApi } from '../../data/course-api';
import { CourseResponse } from '../../data/course.models';
import { AuthState } from '../../../../core/auth-state';

@Component({
  selector: 'app-course-list',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, TableModule, ButtonModule, ToastModule, DialogModule, InputText, ConfirmDialogModule],
  providers: [MessageService, ConfirmationService],
  templateUrl: './course-list.html',
  styleUrl: './course-list.scss',
})
export class CourseList implements OnInit {
  private courseApi = inject(CourseApi);
  private messageService = inject(MessageService);
  private confirmationService = inject(ConfirmationService);
  private fb = inject(FormBuilder);
  authState = inject(AuthState);

  $courses = signal<CourseResponse[]>([]);
  $loading = signal(false);
  dialogVisible = false;
  $editId = signal<number | null>(null);

  colorOptions = ['#3B82F6','#10B981','#F59E0B','#EF4444','#8B5CF6','#EC4899','#06B6D4'];

  form = this.fb.group({
    name: ['', Validators.required],
    color: ['#3B82F6'],
    description: [''],
  });

  ngOnInit() { this.load(); }

  load() {
    this.$loading.set(true);
    this.courseApi.getAll().subscribe({
      next: (d) => { this.$courses.set(d); this.$loading.set(false); },
      error: () => { this.$loading.set(false); },
    });
  }

  openCreate() {
    this.$editId.set(null);
    this.form.reset({ color: '#3B82F6' });
    this.dialogVisible = true;
  }

  openEdit(c: CourseResponse) {
    this.$editId.set(c.id);
    this.form.patchValue({ name: c.name, color: c.color, description: c.description });
    this.dialogVisible = true;
  }

  submit() {
    if (this.form.invalid) return;
    const dto = this.form.value as any;
    const id = this.$editId();
    const req = id ? this.courseApi.update(id, dto) : this.courseApi.save(dto);
    req.subscribe({
      next: () => { this.messageService.add({ severity: 'success', summary: 'Guardado', detail: 'Curso guardado' }); this.dialogVisible = false; this.load(); },
      error: () => this.messageService.add({ severity: 'error', summary: 'Error', detail: 'No se pudo guardar' }),
    });
  }

  confirmDelete(c: CourseResponse) {
    this.confirmationService.confirm({
      message: `¿Eliminar curso "${c.name}"?`,
      header: 'Confirmar',
      icon: 'pi pi-trash',
      accept: () => {
        this.courseApi.delete(c.id).subscribe({
          next: () => { this.messageService.add({ severity: 'success', summary: 'Eliminado', detail: 'Curso eliminado' }); this.load(); },
          error: () => this.messageService.add({ severity: 'error', summary: 'Error', detail: 'No se pudo eliminar' }),
        });
      },
    });
  }
}
