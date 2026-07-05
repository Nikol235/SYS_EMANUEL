package pe.edu.upeu.colegio_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class StudentRequestDTO {

    @NotBlank(message = "El nombre es requerido")
    private String firstName;

    @NotBlank(message = "El apellido es requerido")
    private String lastName;

    private String dni;
    private LocalDate birthDate;

    @NotNull(message = "El grado es requerido")
    private Long gradeLevelId;

    private String photoUrl;
    private String parentPhone;
    private BigDecimal monthlyFee;
    private Boolean active;
}
