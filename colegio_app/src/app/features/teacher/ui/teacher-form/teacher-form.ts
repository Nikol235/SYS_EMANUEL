import { Component, inject, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { InputText } from 'primeng/inputtext';
import { Button } from 'primeng/button';
import { Toast } from 'primeng/toast';
import { MessageService } from 'primeng/api';
import { UserApi } from '../../../user/data/user-api';

@Component({
  selector: 'app-teacher-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, InputText, Button, Toast],
  providers: [MessageService],
  templateUrl: './teacher-form.html',
  styleUrl: './teacher-form.scss',
})
export class TeacherForm implements OnInit {
  private userApi = inject(UserApi);
  private messageService = inject(MessageService);
  private router = inject(Router);
  private route = inject(ActivatedRoute);
  private fb = inject(FormBuilder);

  $loading = signal(false);
  $editId = signal<number | null>(null);

  form = this.fb.group({
    firstName: ['', Validators.required],
    lastName: ['', Validators.required],
    dni: ['', [Validators.required, Validators.minLength(8), Validators.maxLength(8)]],
    email: [''],
    phone: [''],
  });

  get previewUsername(): string {
    const f = this.form.value.firstName ?? '';
    const l = this.form.value.lastName ?? '';
    if (!f && !l) return '';
    return `${this.normalize(f)}.${this.normalize(l)}`;
  }

  ngOnInit() {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.$editId.set(Number(id));
      this.userApi.getById(Number(id)).subscribe(u => {
        const parts = (u.fullName ?? '').split(' ');
        const firstName = parts[0] ?? '';
        const lastName = parts.slice(1).join(' ');
        this.form.patchValue({ firstName, lastName, dni: u.dni, email: u.email, phone: u.phone });
      });
    }
  }

  submit() {
    if (this.form.invalid) return;
    this.$loading.set(true);
    const v = this.form.value;
    const username = `${this.normalize(v.firstName!)}.${this.normalize(v.lastName!)}`;
    const dto: any = {
      username,
      role: 'docente',
      fullName: `${v.firstName!.trim()} ${v.lastName!.trim()}`,
      dni: v.dni,
      email: v.email || undefined,
      phone: v.phone || undefined,
      active: true,
    };
    if (!this.$editId()) dto.password = v.dni;

    const id = this.$editId();
    const req = id ? this.userApi.update(id, dto) : this.userApi.save(dto);
    req.subscribe({
      next: () => {
        this.messageService.add({
          severity: 'success',
          summary: 'Guardado',
          detail: id ? 'Docente actualizado' : 'Docente registrado',
        });
        setTimeout(() => this.router.navigate(['/teachers']), 1000);
      },
      error: () => {
        this.$loading.set(false);
        this.messageService.add({ severity: 'error', summary: 'Error', detail: 'No se pudo guardar' });
      },
    });
  }

  cancel() { this.router.navigate(['/teachers']); }

  private normalize(s: string): string {
    return s.trim().toLowerCase()
      .normalize('NFD').replace(/\p{Diacritic}/gu, '')
      .replace(/\s+/g, '');
  }
}
