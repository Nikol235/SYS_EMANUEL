export interface GradeLevelResponse {
  id: number;
  name: string;
  section: string;
  color?: string;
  photoUrl?: string;
  period?: { id: number; name: string };
  tutor?: { id: number; fullName: string; photoUrl?: string };
  memberCount?: number;
}

export interface GradeLevelRequest {
  name: string;
  section: string;
  color?: string;
  photoUrl?: string;
  periodId?: number;
  tutorId?: number;
}
