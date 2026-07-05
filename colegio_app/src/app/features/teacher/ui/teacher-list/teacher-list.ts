import { Component, inject, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { TableModule } from 'primeng/table';
import { ButtonModule } from 'primeng/button';
import { ToastModule } from 'primeng/toast';
import { TagModule } from 'primeng/tag';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { MessageService, ConfirmationService } from 'primeng/api';
import { UserApi } from '../../../user/data/user-api';
import { UserResponse } from '../../../user/data/user.models';

@Component({
  selector: 'app-teacher-list',
  standalone: true,
  imports: [CommonModule, TableModule, ButtonModule, ToastModule, TagModule, ConfirmDialogModule],
  providers: [MessageService, ConfirmationService],
  templateUrl: './teacher-list.html',
})
export class TeacherList implements OnInit {
  private userApi = inject(UserApi);
  private messageService = inject(MessageService);
  private confirmationService = inject(ConfirmationService);
  private router = inject(Router);

  $teachers = signal<UserResponse[]>([]);
  $loading = signal(false);

  ngOnInit() { this.load(); }

  load() {
    this.$loading.set(true);
    this.userApi.getStaff().subscribe({
      next: (users) => { this.$teachers.set(users.filter(u => u.role === 'docente')); this.$loading.set(false); },
      error: () => this.$loading.set(false),
    });
  }

  create() { this.router.navigate(['/teachers/create']); }
  edit(t: UserResponse) { this.router.navigate(['/teachers/edit', t.id]); }

  confirmDelete(t: UserResponse) {
    this.confirmationService.confirm({
      message: `¿Eliminar a ${t.fullName}?`,
      header: 'Confirmar',
      icon: 'pi pi-trash',
      accept: () => {
        this.userApi.delete(t.id).subscribe({
          next: () => { this.messageService.add({ severity: 'success', summary: 'Eliminado' }); this.load(); },
          error: () => this.messageService.add({ severity: 'error', summary: 'Error', detail: 'No se pudo eliminar' }),
        });
      },
    });
  }
}
