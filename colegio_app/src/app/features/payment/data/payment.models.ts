export interface PaymentResponse {
  id: number;
  month: string;
  year: number;
  amount: number;
  paid: boolean;
  paidDate?: string;
  student: { id: number; firstName: string; lastName: string };
}

export interface PaymentRequest {
  studentId?: number;
  month?: string;
  year?: number;
  amount?: number;
  paid?: boolean;
  paidDate?: string;
}
