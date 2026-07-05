package pe.edu.upeu.colegio_api.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserRequestDTO {

    @NotBlank(message = "El usuario es requerido")
    private String username;

    private String password;

    @NotBlank(message = "El rol es requerido")
    private String role;

    private String fullName;
    private String dni;
    private String email;
    private String phone;
    private String photoUrl;
    private Boolean active;
}
