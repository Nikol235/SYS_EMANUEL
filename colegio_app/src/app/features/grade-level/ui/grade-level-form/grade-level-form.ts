import { Component, inject, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { InputText } from 'primeng/inputtext';
import { Select } from 'primeng/select';
import { Button } from 'primeng/button';
import { Toast } from 'primeng/toast';
import { MessageService } from 'primeng/api';
import { GradeLevelApi } from '../../data/grade-level-api';
import { UserApi } from '../../../user/data/user-api';
import { UserResponse } from '../../../user/data/user.models';

@Component({
  selector: 'app-grade-level-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, InputText, Select, Button, Toast],
  providers: [MessageService],
  templateUrl: './grade-level-form.html',
  styleUrl: './grade-level-form.scss',
})
export class GradeLevelForm implements OnInit {
  private gradeLevelApi = inject(GradeLevelApi);
  private userApi = inject(UserApi);
  private messageService = inject(MessageService);
  private router = inject(Router);
  private route = inject(ActivatedRoute);
  private fb = inject(FormBuilder);

  $teachers = signal<UserResponse[]>([]);
  $loading = signal(false);
  $editId = signal<number | null>(null);

  colorOptions = ['#3B82F6','#10B981','#F59E0B','#EF4444','#8B5CF6','#EC4899','#06B6D4'];

  form = this.fb.group({
    name: ['', Validators.required],
    section: ['', Validators.required],
    color: ['#3B82F6'],
    tutorId: [null as number | null],
  });

  ngOnInit() {
    this.userApi.getStaff().subscribe(u => this.$teachers.set(u.filter(x => x.role === 'docente')));
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.$editId.set(Number(id));
      this.gradeLevelApi.getById(Number(id)).subscribe(gl => {
        this.form.patchValue({ name: gl.name, section: gl.section, color: gl.color, tutorId: gl.tutor?.id ?? null });
      });
    }
  }

  submit() {
    if (this.form.invalid) return;
    this.$loading.set(true);
    const dto: any = this.form.value;
    const id = this.$editId();
    const req = id ? this.gradeLevelApi.update(id, dto) : this.gradeLevelApi.save(dto);
    req.subscribe({
      next: () => { this.messageService.add({ severity: 'success', summary: 'Guardado', detail: 'Grado guardado' }); setTimeout(() => this.router.navigate(['/grade-levels']), 1000); },
      error: () => { this.$loading.set(false); this.messageService.add({ severity: 'error', summary: 'Error', detail: 'No se pudo guardar' }); },
    });
  }

  cancel() { this.router.navigate(['/grade-levels']); }
}
