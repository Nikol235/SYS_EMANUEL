package pe.edu.upeu.colegio_api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class DashboardAdminDTO {
    private long totalTeachers;
    private long totalStudents;
    private long totalCourses;
    private long pendingPayments;
    private BigDecimal pendingAmount;
}
