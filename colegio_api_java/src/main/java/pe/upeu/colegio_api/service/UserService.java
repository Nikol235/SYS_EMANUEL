package pe.edu.upeu.colegio_api.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pe.edu.upeu.colegio_api.dto.UserRequestDTO;
import pe.edu.upeu.colegio_api.dto.UserResponseDTO;

import java.util.List;

public interface UserService {
    List<UserResponseDTO> findAll();
    Page<UserResponseDTO> findAll(Pageable pageable);
    List<UserResponseDTO> findStaff();
    UserResponseDTO findById(Long id);
    UserResponseDTO create(UserRequestDTO dto);
    UserResponseDTO update(Long id, UserRequestDTO dto);
    void delete(Long id);
}
