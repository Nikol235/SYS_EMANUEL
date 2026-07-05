export interface TeacherCourseResponse {
  id: number;
  evalNames?: string;
  teacher: { id: number; fullName: string };
  course: { id: number; name: string; color: string };
  gradeLevel: { id: number; name: string; section: string };
}

export interface TeacherCourseRequest {
  teacherId: number;
  courseId: number;
  gradeLevelId: number;
  periodId?: number;
}
