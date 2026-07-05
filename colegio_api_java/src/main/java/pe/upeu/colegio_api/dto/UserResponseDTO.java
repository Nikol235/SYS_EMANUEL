package pe.edu.upeu.colegio_api.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserResponseDTO {
    private Long id;
    private String username;
    private String role;
    private String fullName;
    private String dni;
    private String email;
    private String phone;
    private String photoUrl;
    private Boolean active;
    private LocalDateTime createdAt;
}
