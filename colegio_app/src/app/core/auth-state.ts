import { computed, Injectable, signal } from '@angular/core';

const TOKEN_KEY = 'auth_token';
const USERNAME_KEY = 'auth_username';
const ROLE_KEY = 'auth_role';
const FULLNAME_KEY = 'auth_fullname';
const PHOTO_KEY = 'auth_photo';
const ID_KEY = 'auth_id';

@Injectable({ providedIn: 'root' })
export class AuthState {
  $token = signal<string | null>(localStorage.getItem(TOKEN_KEY));
  $username = signal<string | null>(localStorage.getItem(USERNAME_KEY));
  $role = signal<string | null>(localStorage.getItem(ROLE_KEY));
  $fullName = signal<string | null>(localStorage.getItem(FULLNAME_KEY));
  $photoUrl = signal<string | null>(localStorage.getItem(PHOTO_KEY));
  $id = signal<number | null>(Number(localStorage.getItem(ID_KEY)) || null);

  $isLoggedIn = computed(() => !!this.$token());
  $isAdmin = computed(() => this.$role() === 'admin');
  $isDirector = computed(() => this.$role() === 'director');
  $isDocente = computed(() => this.$role() === 'docente');
  $isPadre = computed(() => this.$role() === 'padre');
  $isAuxiliar = computed(() => this.$role() === 'auxiliar');
  $isSecretaria = computed(() => this.$role() === 'secretaria');

  $canManageUsers = computed(() =>
    ['admin', 'director'].includes(this.$role() ?? '')
  );
  $canManageStudents = computed(() =>
    ['admin', 'director', 'secretaria'].includes(this.$role() ?? '')
  );
  $canViewAll = computed(() =>
    ['admin', 'director', 'secretaria', 'auxiliar'].includes(this.$role() ?? '')
  );

  setSession(token: string, id: number, username: string, role: string, fullName: string, photoUrl?: string) {
    localStorage.setItem(TOKEN_KEY, token);
    localStorage.setItem(ID_KEY, String(id));
    localStorage.setItem(USERNAME_KEY, username);
    localStorage.setItem(ROLE_KEY, role);
    localStorage.setItem(FULLNAME_KEY, fullName);
    if (photoUrl) localStorage.setItem(PHOTO_KEY, photoUrl);
    this.$token.set(token);
    this.$id.set(id);
    this.$username.set(username);
    this.$role.set(role);
    this.$fullName.set(fullName);
    this.$photoUrl.set(photoUrl ?? null);
  }

  clearSession() {
    [TOKEN_KEY, ID_KEY, USERNAME_KEY, ROLE_KEY, FULLNAME_KEY, PHOTO_KEY].forEach(k =>
      localStorage.removeItem(k)
    );
    this.$token.set(null);
    this.$id.set(null);
    this.$username.set(null);
    this.$role.set(null);
    this.$fullName.set(null);
    this.$photoUrl.set(null);
  }
}
