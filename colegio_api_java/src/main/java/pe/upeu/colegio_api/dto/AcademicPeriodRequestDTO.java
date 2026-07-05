package pe.edu.upeu.colegio_api.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AcademicPeriodRequestDTO {

    @NotBlank(message = "El nombre es requerido")
    private String name;
}
