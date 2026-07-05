import { Component, inject } from '@angular/core';
import { Toolbar } from 'primeng/toolbar';
import { Button } from 'primeng/button';
import { Avatar } from 'primeng/avatar';
import { Router } from '@angular/router';
import { LayoutState } from '../../core/layout-state';
import { AuthState } from '../../core/auth-state';

@Component({
  selector: 'app-header-layout',
  standalone: true,
  imports: [Toolbar, Button, Avatar],
  templateUrl: './header-layout.html',
  styleUrl: './header-layout.scss',
})
export class HeaderLayout {
  layoutState = inject(LayoutState);
  authState = inject(AuthState);
  private router = inject(Router);

  logout() {
    this.authState.clearSession();
    this.router.navigate(['/auth/login']);
  }
}
