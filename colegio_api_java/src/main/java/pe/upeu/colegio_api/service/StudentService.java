package pe.edu.upeu.colegio_api.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pe.edu.upeu.colegio_api.dto.StudentRequestDTO;
import pe.edu.upeu.colegio_api.dto.StudentResponseDTO;

import java.util.List;

public interface StudentService {
    List<StudentResponseDTO> findAll(String username, String role);
    Page<StudentResponseDTO> findAllPaged(Pageable pageable);
    StudentResponseDTO findById(Long id);
    StudentResponseDTO create(StudentRequestDTO dto, String creatorRole, String creatorName);
    StudentResponseDTO update(Long id, StudentRequestDTO dto);
    void delete(Long id);
    void generatePayments();
}
