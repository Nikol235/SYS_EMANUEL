package pe.edu.upeu.colegio_api.service;

import pe.edu.upeu.colegio_api.dto.GradeLevelRequestDTO;
import pe.edu.upeu.colegio_api.dto.GradeLevelResponseDTO;
import pe.edu.upeu.colegio_api.dto.StudentResponseDTO;

import java.util.List;

public interface GradeLevelService {
    List<GradeLevelResponseDTO> findAll();
    GradeLevelResponseDTO findById(Long id);
    GradeLevelResponseDTO create(GradeLevelRequestDTO dto);
    GradeLevelResponseDTO update(Long id, GradeLevelRequestDTO dto);
    void delete(Long id);
    List<StudentResponseDTO> findMembers(Long groupId);
    void addMember(Long groupId, Long studentId);
    void removeMember(Long groupId, Long studentId);
}
