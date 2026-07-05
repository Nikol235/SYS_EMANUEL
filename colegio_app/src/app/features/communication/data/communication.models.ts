export interface CommunicationResponse {
  id: number;
  title: string;
  body: string;
  type: string;
  attachments?: string;
  studentIds?: string;
  createdAt: string;
  author: { id: number; fullName: string; role: string };
  course?: { id: number; name: string; color: string };
  gradeLevel?: { id: number; name: string };
}

export interface CommunicationRequest {
  title: string;
  body: string;
  type?: string;
  courseId?: number;
  gradeLevelId?: number;
  attachments?: string[];
  studentIds?: number[];
}

export interface CommunicationPage {
  content: CommunicationResponse[];
  totalElements: number;
  totalPages: number;
  size: number;
  number: number;
}
