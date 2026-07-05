import { Component, computed, inject, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TableModule } from 'primeng/table';
import { ButtonModule } from 'primeng/button';
import { TagModule } from 'primeng/tag';
import { ToastModule } from 'primeng/toast';
import { SelectModule } from 'primeng/select';
import { DialogModule } from 'primeng/dialog';
import { InputText } from 'primeng/inputtext';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { MessageService } from 'primeng/api';
import { PaymentApi } from '../../data/payment-api';
import { PaymentResponse } from '../../data/payment.models';
import { StudentApi } from '../../../student/data/student-api';
import { AuthState } from '../../../../core/auth-state';

@Component({
  selector: 'app-payment-list',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, TableModule, ButtonModule, TagModule, ToastModule, SelectModule, DialogModule, InputText],
  providers: [MessageService],
  templateUrl: './payment-list.html',
  styleUrl: './payment-list.scss',
})
export class PaymentList implements OnInit {
  private paymentApi = inject(PaymentApi);
  private studentApi = inject(StudentApi);
  private messageService = inject(MessageService);
  private fb = inject(FormBuilder);
  authState = inject(AuthState);

  $payments = signal<PaymentResponse[]>([]);
  $students = signal<any[]>([]);
  $loading = signal(false);
  createDialogVisible = false;

  $studentOptions = computed(() =>
    this.$students().map(s => ({ label: `${s.lastName}, ${s.firstName}`, value: s.id }))
  );

  monthOptions = [
    { label: 'Marzo', value: 'Marzo' }, { label: 'Abril', value: 'Abril' },
    { label: 'Mayo', value: 'Mayo' }, { label: 'Junio', value: 'Junio' },
    { label: 'Julio', value: 'Julio' }, { label: 'Agosto', value: 'Agosto' },
    { label: 'Septiembre', value: 'Septiembre' }, { label: 'Octubre', value: 'Octubre' },
    { label: 'Noviembre', value: 'Noviembre' }, { label: 'Diciembre', value: 'Diciembre' },
  ];

  filterForm = this.fb.group({
    studentId: [null as number | null],
  });

  createForm = this.fb.group({
    studentId: [null as number | null, Validators.required],
    month: [null as string | null, Validators.required],
    year: [new Date().getFullYear(), Validators.required],
    amount: [350, [Validators.required, Validators.min(1)]],
  });

  ngOnInit() {
    if (!this.authState.$isPadre()) {
      this.studentApi.getAll().subscribe(s => this.$students.set(s));
    }
    this.load();
  }

  load() {
    this.$loading.set(true);
    const studentId = this.filterForm.value.studentId ?? undefined;
    this.paymentApi.getAll(studentId).subscribe({
      next: (d) => { this.$payments.set(d); this.$loading.set(false); },
      error: () => this.$loading.set(false),
    });
  }

  markPaid(p: PaymentResponse) {
    const today = new Date().toISOString().slice(0, 10);
    this.paymentApi.update(p.id, { paid: true, paidDate: today }).subscribe({
      next: () => { this.messageService.add({ severity: 'success', summary: 'Pagado', detail: `${p.month} ${p.year} marcado como pagado` }); this.load(); },
      error: () => this.messageService.add({ severity: 'error', summary: 'Error', detail: 'No se pudo actualizar' }),
    });
  }

  openCreate() {
    this.createForm.reset({ year: new Date().getFullYear(), amount: 350 });
    this.createDialogVisible = true;
  }

  submitCreate() {
    if (this.createForm.invalid) return;
    const v = this.createForm.value;
    this.paymentApi.create({
      studentId: v.studentId!,
      month: v.month!,
      year: v.year!,
      amount: v.amount!,
      paid: false,
    }).subscribe({
      next: () => {
        this.messageService.add({ severity: 'success', summary: 'Creado', detail: 'Pago registrado' });
        this.createDialogVisible = false;
        this.load();
      },
      error: () => this.messageService.add({ severity: 'error', summary: 'Error', detail: 'No se pudo crear el pago' }),
    });
  }
}
