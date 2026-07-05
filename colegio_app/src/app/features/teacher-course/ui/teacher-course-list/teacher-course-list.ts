import { Component, inject, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { TableModule } from 'primeng/table';
import { ButtonModule } from 'primeng/button';
import { ToastModule } from 'primeng/toast';
import { DialogModule } from 'primeng/dialog';
import { Select } from 'primeng/select';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { MessageService, ConfirmationService } from 'primeng/api';
import { TeacherCourseApi } from '../../data/teacher-course-api';
import { TeacherCourseResponse } from '../../data/teacher-course.models';
import { UserApi } from '../../../user/data/user-api';
import { CourseApi } from '../../../course/data/course-api';
import { GradeLevelApi } from '../../../grade-level/data/grade-level-api';
import { AuthState } from '../../../../core/auth-state';

@Component({
  selector: 'app-teacher-course-list',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, TableModule, ButtonModule, ToastModule, DialogModule, Select, ConfirmDialogModule],
  providers: [MessageService, ConfirmationService],
  templateUrl: './teacher-course-list.html',
  styleUrl: './teacher-course-list.scss',
})
export class TeacherCourseList implements OnInit {
  private api = inject(TeacherCourseApi);
  private userApi = inject(UserApi);
  private courseApi = inject(CourseApi);
  private gradeLevelApi = inject(GradeLevelApi);
  private messageService = inject(MessageService);
  private confirmationService = inject(ConfirmationService);
  private fb = inject(FormBuilder);
  authState = inject(AuthState);

  $items = signal<TeacherCourseResponse[]>([]);
  $teachers = signal<any[]>([]);
  $courses = signal<any[]>([]);
  $gradeLevels = signal<any[]>([]);
  dialogVisible = false;
  $loading = signal(false);

  form = this.fb.group({
    teacherId: [null as number | null, Validators.required],
    courseId: [null as number | null, Validators.required],
    gradeLevelId: [null as number | null, Validators.required],
  });

  ngOnInit() {
    this.load();
    this.userApi.getStaff().subscribe(u => this.$teachers.set(u.filter(x => x.role === 'docente')));
    this.courseApi.getAll().subscribe(c => this.$courses.set(c));
    this.gradeLevelApi.getAll().subscribe(g => this.$gradeLevels.set(g));
  }

  load() {
    this.$loading.set(true);
    this.api.getAll().subscribe({
      next: (d) => { this.$items.set(d); this.$loading.set(false); },
      error: () => this.$loading.set(false),
    });
  }

  submit() {
    if (this.form.invalid) return;
    this.api.save(this.form.value as any).subscribe({
      next: () => { this.messageService.add({ severity: 'success', summary: 'Guardado', detail: 'Asignación creada' }); this.dialogVisible = false; this.load(); },
      error: () => this.messageService.add({ severity: 'error', summary: 'Error', detail: 'No se pudo guardar' }),
    });
  }

  confirmDelete(item: TeacherCourseResponse) {
    this.confirmationService.confirm({
      message: `¿Eliminar asignación de ${item.teacher?.fullName}?`,
      header: 'Confirmar',
      icon: 'pi pi-trash',
      accept: () => {
        this.api.delete(item.id).subscribe({
          next: () => { this.messageService.add({ severity: 'success', summary: 'Eliminado', detail: 'Asignación eliminada' }); this.load(); },
          error: () => this.messageService.add({ severity: 'error', summary: 'Error', detail: 'No se pudo eliminar' }),
        });
      },
    });
  }
}
