import { Component, inject, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, ReactiveFormsModule } from '@angular/forms';
import { TableModule } from 'primeng/table';
import { ButtonModule } from 'primeng/button';
import { ToastModule } from 'primeng/toast';
import { SelectModule } from 'primeng/select';
import { InputNumber } from 'primeng/inputnumber';
import { DialogModule } from 'primeng/dialog';
import { MessageService } from 'primeng/api';
import { GradeApi } from '../../data/grade-api';
import { GradeResponse } from '../../data/grade.models';
import { StudentApi } from '../../../student/data/student-api';
import { TeacherCourseApi } from '../../../teacher-course/data/teacher-course-api';
import { AuthState } from '../../../../core/auth-state';

@Component({
  selector: 'app-grade-list',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, TableModule, ButtonModule, ToastModule, SelectModule, InputNumber, DialogModule],
  providers: [MessageService],
  templateUrl: './grade-list.html',
  styleUrl: './grade-list.scss',
})
export class GradeList implements OnInit {
  private gradeApi = inject(GradeApi);
  private studentApi = inject(StudentApi);
  private tcApi = inject(TeacherCourseApi);
  private messageService = inject(MessageService);
  private fb = inject(FormBuilder);
  authState = inject(AuthState);

  $grades = signal<GradeResponse[]>([]);
  $students = signal<any[]>([]);
  $teacherCourses = signal<any[]>([]);
  $loading = signal(false);
  dialogVisible = false;

  filterForm = this.fb.group({
    studentId: [null as number | null],
    teacherCourseId: [null as number | null],
  });

  gradeForm = this.fb.group({
    studentId: [null as number | null],
    teacherCourseId: [null as number | null],
    evaluationName: [''],
    score: [null as number | null],
  });

  ngOnInit() {
    this.studentApi.getAll().subscribe(s => this.$students.set(s));
    this.tcApi.getAll().subscribe(t => this.$teacherCourses.set(t));
    this.load();
  }

  load() {
    this.$loading.set(true);
    const v = this.filterForm.value;
    this.gradeApi.getAll(v.studentId ?? undefined, v.teacherCourseId ?? undefined).subscribe({
      next: (d) => { this.$grades.set(d); this.$loading.set(false); },
      error: () => this.$loading.set(false),
    });
  }

  submit() {
    if (this.gradeForm.invalid) return;
    this.gradeApi.save(this.gradeForm.value as any).subscribe({
      next: () => { this.messageService.add({ severity: 'success', summary: 'Guardado', detail: 'Nota guardada' }); this.dialogVisible = false; this.load(); },
      error: () => this.messageService.add({ severity: 'error', summary: 'Error', detail: 'No se pudo guardar' }),
    });
  }
}
