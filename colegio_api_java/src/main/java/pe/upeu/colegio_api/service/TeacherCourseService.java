package pe.edu.upeu.colegio_api.service;

import pe.edu.upeu.colegio_api.dto.TeacherCourseRequestDTO;
import pe.edu.upeu.colegio_api.dto.TeacherCourseResponseDTO;

import java.util.List;

public interface TeacherCourseService {
    List<TeacherCourseResponseDTO> findAll(String username, String role);
    TeacherCourseResponseDTO create(TeacherCourseRequestDTO dto);
    TeacherCourseResponseDTO updateEvalNames(Long id, List<String> evalNames, String username, String role);
    void delete(Long id);
}
