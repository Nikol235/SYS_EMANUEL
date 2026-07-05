package pe.edu.upeu.colegio_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pe.edu.upeu.colegio_api.entity.Payment;

import java.math.BigDecimal;
import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    List<Payment> findByStudentIdOrderByYearAscMonthAsc(Long studentId);

    List<Payment> findByStudentIdAndPaidFalse(Long studentId);

    void deleteByStudentId(Long studentId);

    @Query("SELECT COUNT(p) FROM Payment p WHERE p.paid = false")
    long countPending();

    @Query("SELECT COALESCE(SUM(p.amount), 0) FROM Payment p WHERE p.paid = false")
    BigDecimal sumPendingAmount();
}
