export interface StudentResponse {
  id: number;
  firstName: string;
  lastName: string;
  dni?: string;
  birthDate?: string;
  gradeLevel: { id: number; name: string; section: string };
  codigo?: string;
  active: boolean;
  photoUrl?: string;
  parentPhone?: string;
  parentId?: number;
  parentUsername?: string;
}

export interface StudentRequest {
  firstName: string;
  lastName: string;
  dni?: string;
  birthDate?: string;
  gradeLevelId: number;
  parentPhone?: string;
  monthlyFee?: number;
  active?: boolean;
  photoUrl?: string;
}
