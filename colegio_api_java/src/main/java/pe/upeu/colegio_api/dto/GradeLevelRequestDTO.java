package pe.edu.upeu.colegio_api.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class GradeLevelRequestDTO {

    @NotBlank(message = "El nombre es requerido")
    private String name;

    private String section;
    private String color;
    private String photoUrl;
    private Long tutorId;
    private Long periodId;
}
