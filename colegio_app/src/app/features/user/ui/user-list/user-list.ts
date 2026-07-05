import { Component, inject, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { TableModule } from 'primeng/table';
import { ButtonModule } from 'primeng/button';
import { TagModule } from 'primeng/tag';
import { ToastModule } from 'primeng/toast';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { MessageService, ConfirmationService } from 'primeng/api';
import { UserApi } from '../../data/user-api';
import { UserResponse } from '../../data/user.models';
import { AuthState } from '../../../../core/auth-state';

@Component({
  selector: 'app-user-list',
  standalone: true,
  imports: [CommonModule, TableModule, ButtonModule, TagModule, ToastModule, ConfirmDialogModule],
  providers: [MessageService, ConfirmationService],
  templateUrl: './user-list.html',
  styleUrl: './user-list.scss',
})
export class UserList implements OnInit {
  private userApi = inject(UserApi);
  private messageService = inject(MessageService);
  private confirmationService = inject(ConfirmationService);
  private router = inject(Router);
  authState = inject(AuthState);

  $users = signal<UserResponse[]>([]);
  $loading = signal(false);

  ngOnInit() { this.load(); }

  load() {
    this.$loading.set(true);
    this.userApi.getAll().subscribe({
      next: (d) => { this.$users.set(d); this.$loading.set(false); },
      error: () => this.$loading.set(false),
    });
  }

  create() { this.router.navigate(['/users/create']); }
  edit(u: UserResponse) { this.router.navigate(['/users/edit', u.id]); }

  confirmDelete(u: UserResponse) {
    this.confirmationService.confirm({
      message: `¿Eliminar usuario "${u.username}"?`,
      header: 'Confirmar',
      icon: 'pi pi-trash',
      accept: () => {
        this.userApi.delete(u.id).subscribe({
          next: () => { this.messageService.add({ severity: 'success', summary: 'Eliminado', detail: 'Usuario eliminado' }); this.load(); },
          error: () => this.messageService.add({ severity: 'error', summary: 'Error', detail: 'No se pudo eliminar' }),
        });
      },
    });
  }

  getRoleSeverity(role: string): 'success' | 'info' | 'warn' | 'danger' | 'secondary' | 'contrast' {
    const map: Record<string, 'success' | 'info' | 'warn' | 'danger' | 'secondary' | 'contrast'> = {
      admin: 'danger', director: 'warn', docente: 'info', secretaria: 'secondary', auxiliar: 'contrast', padre: 'success',
    };
    return map[role] ?? 'info';
  }
}
