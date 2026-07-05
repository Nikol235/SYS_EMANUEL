package pe.edu.upeu.colegio_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class PaymentRequestDTO {

    @NotNull(message = "El alumno es requerido")
    private Long studentId;

    @NotBlank(message = "El mes es requerido")
    private String month;

    @NotNull(message = "El año es requerido")
    private Integer year;

    @NotNull(message = "El monto es requerido")
    private BigDecimal amount;

    private Boolean paid;
    private LocalDate paidDate;
}
