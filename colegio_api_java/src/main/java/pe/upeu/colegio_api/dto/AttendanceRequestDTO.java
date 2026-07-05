package pe.edu.upeu.colegio_api.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class AttendanceRequestDTO {

    @NotNull(message = "El alumno es requerido")
    private Long studentId;

    @NotNull(message = "La fecha es requerida")
    private LocalDate date;

    @NotNull(message = "El estado es requerido")
    private String status;

    private String turno = "mañana";
    private String tipo = "entrada";
}
