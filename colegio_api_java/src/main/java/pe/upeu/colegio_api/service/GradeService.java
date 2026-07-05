package pe.edu.upeu.colegio_api.service;

import pe.edu.upeu.colegio_api.dto.GradeRequestDTO;
import pe.edu.upeu.colegio_api.dto.GradeResponseDTO;

import java.util.List;

public interface GradeService {
    List<GradeResponseDTO> findAll(Long studentId, Long teacherCourseId, String username, String role);
    GradeResponseDTO saveOrUpdate(GradeRequestDTO dto, String username, String role);
    GradeResponseDTO update(Long id, Double score);
    void delete(Long id);
}
