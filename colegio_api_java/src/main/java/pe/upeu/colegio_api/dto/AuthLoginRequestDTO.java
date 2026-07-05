package pe.edu.upeu.colegio_api.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AuthLoginRequestDTO {

    @NotBlank(message = "El usuario es requerido")
    private String username;

    @NotBlank(message = "La contraseña es requerida")
    private String password;
}
