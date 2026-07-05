package pe.edu.upeu.colegio_api.service;

import pe.edu.upeu.colegio_api.dto.CourseRequestDTO;
import pe.edu.upeu.colegio_api.dto.CourseResponseDTO;

import java.util.List;

public interface CourseService {
    List<CourseResponseDTO> findAll();
    CourseResponseDTO findById(Long id);
    CourseResponseDTO create(CourseRequestDTO dto);
    CourseResponseDTO update(Long id, CourseRequestDTO dto);
    void delete(Long id);
}
