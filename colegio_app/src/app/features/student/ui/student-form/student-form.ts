import { Component, inject, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { InputText } from 'primeng/inputtext';
import { Select } from 'primeng/select';
import { DatePicker } from 'primeng/datepicker';
import { Button } from 'primeng/button';
import { Toast } from 'primeng/toast';
import { MessageService } from 'primeng/api';
import { StudentApi } from '../../data/student-api';
import { GradeLevelApi } from '../../../grade-level/data/grade-level-api';
import { GradeLevelResponse } from '../../../grade-level/data/grade-level.models';

@Component({
  selector: 'app-student-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, InputText, Select, DatePicker, Button, Toast],
  providers: [MessageService],
  templateUrl: './student-form.html',
  styleUrl: './student-form.scss',
})
export class StudentForm implements OnInit {
  private studentApi = inject(StudentApi);
  private gradeLevelApi = inject(GradeLevelApi);
  private messageService = inject(MessageService);
  private router = inject(Router);
  private route = inject(ActivatedRoute);
  private fb = inject(FormBuilder);

  $gradeLevels = signal<GradeLevelResponse[]>([]);
  $loading = signal(false);
  $editId = signal<number | null>(null);

  get previewParentUsername(): string {
    const f = this.form.value.firstName ?? '';
    const l = this.form.value.lastName ?? '';
    if (!f && !l) return '';
    const firstF = f.trim().split(/\s+/)[0] ?? '';
    const firstL = l.trim().split(/\s+/)[0] ?? '';
    return `${this.normalize(firstF)}.${this.normalize(firstL)}`;
  }

  private normalize(s: string): string {
    return s.toLowerCase()
      .normalize('NFD').replace(/\p{Diacritic}/gu, '')
      .replace(/[^a-z0-9]/g, '');
  }

  form = this.fb.group({
    firstName: ['', Validators.required],
    lastName: ['', Validators.required],
    dni: [''],
    birthDate: [null as Date | null],
    gradeLevelId: [null as number | null, Validators.required],
    parentPhone: [''],
    monthlyFee: [350],
  });

  ngOnInit() {
    this.gradeLevelApi.getAll().subscribe(data => this.$gradeLevels.set(data));

    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.$editId.set(Number(id));
      this.studentApi.getById(Number(id)).subscribe(s => {
        this.form.patchValue({
          firstName: s.firstName,
          lastName: s.lastName,
          dni: s.dni,
          birthDate: s.birthDate ? new Date(s.birthDate) : null,
          gradeLevelId: s.gradeLevel?.id,
          parentPhone: s.parentPhone,
        });
      });
    }
  }

  submit() {
    if (this.form.invalid) return;
    this.$loading.set(true);
    const v = this.form.value;
    const dto: any = {
      firstName: v.firstName,
      lastName: v.lastName,
      dni: v.dni || undefined,
      birthDate: v.birthDate ? (v.birthDate as Date).toISOString().slice(0, 10) : undefined,
      gradeLevelId: v.gradeLevelId,
      parentPhone: v.parentPhone || undefined,
      monthlyFee: v.monthlyFee,
    };

    const id = this.$editId();
    const req = id ? this.studentApi.update(id, dto) : this.studentApi.save(dto);
    req.subscribe({
      next: () => {
        this.messageService.add({ severity: 'success', summary: 'Guardado', detail: id ? 'Alumno actualizado' : 'Alumno registrado' });
        setTimeout(() => this.router.navigate(['/students']), 1000);
      },
      error: () => { this.$loading.set(false); this.messageService.add({ severity: 'error', summary: 'Error', detail: 'No se pudo guardar' }); },
    });
  }

  cancel() { this.router.navigate(['/students']); }
}
