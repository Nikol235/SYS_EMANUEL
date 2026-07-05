package pe.edu.upeu.colegio_api.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class PaymentResponseDTO {
    private Long id;
    private String month;
    private Integer year;
    private BigDecimal amount;
    private Boolean paid;
    private LocalDate paidDate;

    private StudentInfo student;

    @Data
    public static class StudentInfo {
        private Long id;
        private String firstName;
        private String lastName;
    }
}
