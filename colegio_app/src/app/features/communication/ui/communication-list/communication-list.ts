import { Component, inject, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { TableModule } from 'primeng/table';
import { ButtonModule } from 'primeng/button';
import { ToastModule } from 'primeng/toast';
import { DialogModule } from 'primeng/dialog';
import { InputText } from 'primeng/inputtext';
import { Textarea } from 'primeng/textarea';
import { SelectModule } from 'primeng/select';
import { TagModule } from 'primeng/tag';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { MessageService, ConfirmationService } from 'primeng/api';
import { CommunicationApi } from '../../data/communication-api';
import { CommunicationResponse } from '../../data/communication.models';
import { AuthState } from '../../../../core/auth-state';

@Component({
  selector: 'app-communication-list',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, TableModule, ButtonModule, ToastModule, DialogModule, InputText, Textarea, SelectModule, TagModule, ConfirmDialogModule],
  providers: [MessageService, ConfirmationService],
  templateUrl: './communication-list.html',
  styleUrl: './communication-list.scss',
})
export class CommunicationList implements OnInit {
  private api = inject(CommunicationApi);
  private messageService = inject(MessageService);
  private confirmationService = inject(ConfirmationService);
  private fb = inject(FormBuilder);
  authState = inject(AuthState);

  $items = signal<CommunicationResponse[]>([]);
  $loading = signal(false);
  dialogVisible = false;
  $totalRecords = signal(0);
  page = 0;
  size = 20;

  typeOptions = [
    { label: 'General', value: 'general' },
    { label: 'Por Grado', value: 'grado' },
    { label: 'Por Curso', value: 'curso' },
  ];

  get typeOptionsForRole() {
    if (this.authState.$isDocente()) {
      return [{ label: 'Por Curso', value: 'curso' }];
    }
    if (this.authState.$isAuxiliar()) {
      return [
        { label: 'General', value: 'general' },
        { label: 'Por Grado', value: 'grado' },
      ];
    }
    return this.typeOptions;
  }

  form = this.fb.group({
    title: ['', Validators.required],
    body: ['', Validators.required],
    type: ['general'],
  });

  ngOnInit() { this.load(); }

  openDialog() {
    const defaultType = this.authState.$isDocente() ? 'curso' : 'general';
    this.form.reset({ type: defaultType });
    this.dialogVisible = true;
  }

  load() {
    this.$loading.set(true);
    this.api.getAll(this.page, this.size).subscribe({
      next: (d) => { this.$items.set(d.content); this.$totalRecords.set(d.totalElements); this.$loading.set(false); },
      error: () => this.$loading.set(false),
    });
  }

  submit() {
    if (this.form.invalid) return;
    this.api.save(this.form.value as any).subscribe({
      next: () => { this.messageService.add({ severity: 'success', summary: 'Enviado', detail: 'Comunicado publicado' }); this.dialogVisible = false; this.load(); },
      error: () => this.messageService.add({ severity: 'error', summary: 'Error', detail: 'No se pudo publicar' }),
    });
  }

  confirmDelete(c: CommunicationResponse) {
    this.confirmationService.confirm({
      message: `¿Eliminar comunicado "${c.title}"?`,
      header: 'Confirmar',
      icon: 'pi pi-trash',
      accept: () => {
        this.api.delete(c.id).subscribe({
          next: () => { this.messageService.add({ severity: 'success', summary: 'Eliminado', detail: 'Comunicado eliminado' }); this.load(); },
          error: () => this.messageService.add({ severity: 'error', summary: 'Error', detail: 'No se pudo eliminar' }),
        });
      },
    });
  }

  canEditDelete(c: CommunicationResponse): boolean {
    const role = this.authState.$role();
    if (role === 'admin' || role === 'director') return true;
    // compare by id since author fullName ≠ username
    const myId = this.authState.$id();
    return c.author?.id === myId;
  }
}
