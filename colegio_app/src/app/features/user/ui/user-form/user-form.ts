import { Component, inject, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { InputText } from 'primeng/inputtext';
import { Password } from 'primeng/password';
import { Select } from 'primeng/select';
import { Button } from 'primeng/button';
import { Toast } from 'primeng/toast';
import { ToggleSwitch } from 'primeng/toggleswitch';
import { MessageService } from 'primeng/api';
import { UserApi } from '../../data/user-api';

@Component({
  selector: 'app-user-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, InputText, Password, Select, Button, Toast, ToggleSwitch],
  providers: [MessageService],
  templateUrl: './user-form.html',
  styleUrl: './user-form.scss',
})
export class UserForm implements OnInit {
  private userApi = inject(UserApi);
  private messageService = inject(MessageService);
  private router = inject(Router);
  private route = inject(ActivatedRoute);
  private fb = inject(FormBuilder);

  $loading = signal(false);
  $editId = signal<number | null>(null);

  roleOptions = [
    { label: 'Administrador', value: 'admin' },
    { label: 'Director', value: 'director' },
    { label: 'Docente', value: 'docente' },
    { label: 'Auxiliar', value: 'auxiliar' },
    { label: 'Secretaria', value: 'secretaria' },
    { label: 'Padre de Familia', value: 'padre' },
  ];

  form = this.fb.group({
    username: ['', Validators.required],
    password: [''],
    role: ['docente', Validators.required],
    fullName: [''],
    dni: [''],
    email: [''],
    phone: [''],
    active: [true],
  });

  ngOnInit() {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.$editId.set(Number(id));
      this.userApi.getById(Number(id)).subscribe(u => {
        this.form.patchValue({ username: u.username, role: u.role, fullName: u.fullName, dni: u.dni, email: u.email, phone: u.phone, active: u.active });
      });
    }
  }

  submit() {
    if (this.form.invalid) return;
    this.$loading.set(true);
    const v = this.form.value;
    const dto: any = { ...v };
    if (!dto.password) delete dto.password;

    const id = this.$editId();
    const req = id ? this.userApi.update(id, dto) : this.userApi.save(dto);
    req.subscribe({
      next: () => { this.messageService.add({ severity: 'success', summary: 'Guardado', detail: 'Usuario guardado' }); setTimeout(() => this.router.navigate(['/users']), 1000); },
      error: () => { this.$loading.set(false); this.messageService.add({ severity: 'error', summary: 'Error', detail: 'No se pudo guardar' }); },
    });
  }

  cancel() { this.router.navigate(['/users']); }
}
