package pe.edu.upeu.colegio_api.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upeu.colegio_api.dto.CommunicationRequestDTO;
import pe.edu.upeu.colegio_api.dto.CommunicationResponseDTO;
import pe.edu.upeu.colegio_api.entity.Communication;
import pe.edu.upeu.colegio_api.exception.AppException;
import pe.edu.upeu.colegio_api.mapper.CommunicationMapper;
import pe.edu.upeu.colegio_api.repository.*;
import pe.edu.upeu.colegio_api.service.CommunicationService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommunicationServiceImpl implements CommunicationService {

    private final CommunicationRepository communicationRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final GradeLevelRepository gradeLevelRepository;
    private final StudentRepository studentRepository;
    private final CommunicationMapper communicationMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<CommunicationResponseDTO> findAll(String username, String role, Pageable pageable) {
        Page<Communication> page;
        if ("padre".equals(role)) {
            Long parentId = userRepository.findByUsername(username).map(u -> u.getId()).orElseThrow();
            List<Long> gradeLevelIds = studentRepository.findByParentId(parentId).stream()
                    .map(s -> s.getGradeLevel().getId()).distinct().toList();
            page = communicationRepository.findForParent(gradeLevelIds, pageable);
        } else if ("docente".equals(role)) {
            Long authorId = userRepository.findByUsername(username).map(u -> u.getId()).orElseThrow();
            page = communicationRepository.findForTeacher(authorId, pageable);
        } else {
            page = communicationRepository.findAllOrderByCreatedAtDesc(pageable);
        }
        return page.map(communicationMapper::toResponseDTO);
    }

    @Override
    @Transactional
    public CommunicationResponseDTO create(CommunicationRequestDTO dto, String username, String role) {
        if ("docente".equals(role) && !"curso".equals(dto.getType()) && !"alumno".equals(dto.getType())) {
            throw new AppException(HttpStatus.BAD_REQUEST, "El docente solo puede enviar comunicados por curso o a alumnos específicos");
        }
        if ("auxiliar".equals(role) && !"general".equals(dto.getType()) && !"grado".equals(dto.getType())) {
            throw new AppException(HttpStatus.BAD_REQUEST, "El auxiliar solo puede enviar comunicados generales o por grado");
        }

        Communication comm = new Communication();
        comm.setAuthor(userRepository.findByUsername(username).orElseThrow());
        comm.setTitle(dto.getTitle());
        comm.setBody(dto.getBody());
        comm.setType(dto.getType() != null ? dto.getType() : "general");
        if (dto.getCourseId() != null) comm.setCourse(courseRepository.findById(dto.getCourseId()).orElse(null));
        if (dto.getGradeLevelId() != null) comm.setGradeLevel(gradeLevelRepository.findById(dto.getGradeLevelId()).orElse(null));
        if (dto.getAttachments() != null && !dto.getAttachments().isEmpty())
            comm.setAttachments(dto.getAttachments().toString());
        if (dto.getStudentIds() != null && !dto.getStudentIds().isEmpty())
            comm.setStudentIds(dto.getStudentIds().toString());

        return communicationMapper.toResponseDTO(communicationRepository.save(comm));
    }

    @Override
    @Transactional
    public CommunicationResponseDTO update(Long id, CommunicationRequestDTO dto, String username, String role) {
        Communication comm = communicationRepository.findById(id)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "Comunicado no encontrado: " + id));
        if (("docente".equals(role) || "auxiliar".equals(role)) && !comm.getAuthor().getUsername().equals(username)) {
            throw new AppException(HttpStatus.FORBIDDEN, "Solo puedes editar tus propios comunicados");
        }
        if (dto.getTitle() != null) comm.setTitle(dto.getTitle());
        if (dto.getBody() != null) comm.setBody(dto.getBody());
        if (dto.getAttachments() != null) comm.setAttachments(dto.getAttachments().toString());
        return communicationMapper.toResponseDTO(communicationRepository.save(comm));
    }

    @Override
    @Transactional
    public void delete(Long id, String username, String role) {
        Communication comm = communicationRepository.findById(id)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "Comunicado no encontrado: " + id));
        if (("docente".equals(role) || "auxiliar".equals(role)) && !comm.getAuthor().getUsername().equals(username)) {
            throw new AppException(HttpStatus.FORBIDDEN, "Solo puedes eliminar tus propios comunicados");
        }
        communicationRepository.delete(comm);
    }
}
