import { Component, computed, inject } from '@angular/core';
import { PanelMenu } from 'primeng/panelmenu';
import { MenuItem } from 'primeng/api';
import { LayoutState } from '../../core/layout-state';
import { AuthState } from '../../core/auth-state';

@Component({
  selector: 'app-sidebar-layout',
  standalone: true,
  imports: [PanelMenu],
  templateUrl: './sidebar-layout.html',
  styleUrl: './sidebar-layout.scss',
})
export class SidebarLayout {
  layoutState = inject(LayoutState);
  authState = inject(AuthState);

  $items = computed<MenuItem[]>(() => {
    const role = this.authState.$role();
    const isAdmin = role === 'admin';
    const isDirector = role === 'director';
    const isDocente = role === 'docente';
    const isPadre = role === 'padre';
    const isAuxiliar = role === 'auxiliar';
    const isSecretaria = role === 'secretaria';
    const canManage = isAdmin || isDirector || isSecretaria;
    const isStaff = isAdmin || isDirector || isAuxiliar || isDocente;

    const items: MenuItem[] = [
      {
        label: 'Dashboard',
        icon: 'pi pi-home',
        routerLink: ['/'],
      },
    ];

    if (isDocente || isAdmin || isDirector) {
      items.push({
        label: 'Académico',
        icon: 'pi pi-book',
        items: [
          { label: 'Mis Asignaciones', icon: 'pi pi-list', routerLink: ['/teacher-courses'] },
          { label: 'Notas', icon: 'pi pi-star', routerLink: ['/grades'] },
          { label: 'Asistencia', icon: 'pi pi-check-square', routerLink: ['/attendance'] },
        ],
      });
    }

    if (isPadre) {
      items.push(
        { label: 'Notas', icon: 'pi pi-star', routerLink: ['/grades'] },
        { label: 'Asistencia', icon: 'pi pi-check-square', routerLink: ['/attendance'] },
        { label: 'Pagos', icon: 'pi pi-credit-card', routerLink: ['/payments'] },
        { label: 'Comunicados', icon: 'pi pi-envelope', routerLink: ['/communications'] },
        { label: 'Horarios', icon: 'pi pi-calendar', routerLink: ['/schedules'] },
      );
    }

    if (canManage || isAuxiliar) {
      items.push({
        label: 'Alumnos',
        icon: 'pi pi-users',
        items: [
          { label: 'Lista de Alumnos', icon: 'pi pi-list', routerLink: ['/students'] },
          { label: 'Grados y Secciones', icon: 'pi pi-sitemap', routerLink: ['/grade-levels'] },
        ],
      });
    }

    if (canManage) {
      items.push({
        label: 'Finanzas',
        icon: 'pi pi-wallet',
        items: [
          { label: 'Pagos', icon: 'pi pi-credit-card', routerLink: ['/payments'] },
        ],
      });
    }

    if (!isPadre) {
      items.push({
        label: 'Comunicados',
        icon: 'pi pi-envelope',
        routerLink: ['/communications'],
      });
    }

    if (isAdmin || isDirector) {
      items.push(
        {
          label: 'Cursos',
          icon: 'pi pi-book',
          items: [
            { label: 'Cursos', icon: 'pi pi-list', routerLink: ['/courses'] },
            { label: 'Asignaciones', icon: 'pi pi-user-plus', routerLink: ['/teacher-courses'] },
          ],
        },
        { label: 'Horarios', icon: 'pi pi-calendar', routerLink: ['/schedules'] },
        { label: 'Docentes', icon: 'pi pi-user-edit', routerLink: ['/teachers'] },
        {
          label: 'Administración',
          icon: 'pi pi-cog',
          items: [
            { label: 'Usuarios', icon: 'pi pi-users', routerLink: ['/users'] },
          ],
        },
      );
    }

    return items;
  });
}
