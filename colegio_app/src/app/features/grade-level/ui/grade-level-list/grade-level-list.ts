import { Component, inject, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { TableModule } from 'primeng/table';
import { ButtonModule } from 'primeng/button';
import { TagModule } from 'primeng/tag';
import { ToastModule } from 'primeng/toast';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { MessageService, ConfirmationService } from 'primeng/api';
import { GradeLevelApi } from '../../data/grade-level-api';
import { GradeLevelResponse } from '../../data/grade-level.models';
import { AuthState } from '../../../../core/auth-state';

@Component({
  selector: 'app-grade-level-list',
  standalone: true,
  imports: [CommonModule, TableModule, ButtonModule, TagModule, ToastModule, ConfirmDialogModule],
  providers: [MessageService, ConfirmationService],
  templateUrl: './grade-level-list.html',
  styleUrl: './grade-level-list.scss',
})
export class GradeLevelList implements OnInit {
  private gradeLevelApi = inject(GradeLevelApi);
  private messageService = inject(MessageService);
  private confirmationService = inject(ConfirmationService);
  private router = inject(Router);
  authState = inject(AuthState);

  $gradeLevels = signal<GradeLevelResponse[]>([]);
  $loading = signal(false);

  ngOnInit() { this.load(); }

  load() {
    this.$loading.set(true);
    this.gradeLevelApi.getAll().subscribe({
      next: (d) => { this.$gradeLevels.set(d); this.$loading.set(false); },
      error: () => { this.$loading.set(false); this.messageService.add({ severity: 'error', summary: 'Error', detail: 'No se pudo cargar grados' }); },
    });
  }

  create() { this.router.navigate(['/grade-levels/create']); }
  edit(gl: GradeLevelResponse) { this.router.navigate(['/grade-levels/edit', gl.id]); }

  confirmDelete(gl: GradeLevelResponse) {
    this.confirmationService.confirm({
      message: `¿Eliminar ${gl.name} ${gl.section}?`,
      header: 'Confirmar',
      icon: 'pi pi-trash',
      accept: () => {
        this.gradeLevelApi.delete(gl.id).subscribe({
          next: () => { this.messageService.add({ severity: 'success', summary: 'Eliminado', detail: 'Grado eliminado' }); this.load(); },
          error: () => this.messageService.add({ severity: 'error', summary: 'Error', detail: 'No se pudo eliminar' }),
        });
      },
    });
  }
}
