package pe.edu.upeu.colegio_api.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upeu.colegio_api.dto.GradeLevelRequestDTO;
import pe.edu.upeu.colegio_api.dto.GradeLevelResponseDTO;
import pe.edu.upeu.colegio_api.dto.StudentResponseDTO;
import pe.edu.upeu.colegio_api.entity.AcademicPeriod;
import pe.edu.upeu.colegio_api.entity.GradeLevel;
import pe.edu.upeu.colegio_api.entity.GroupMember;
import pe.edu.upeu.colegio_api.entity.Student;
import pe.edu.upeu.colegio_api.exception.AppException;
import pe.edu.upeu.colegio_api.mapper.GradeLevelMapper;
import pe.edu.upeu.colegio_api.mapper.StudentMapper;
import pe.edu.upeu.colegio_api.repository.*;
import pe.edu.upeu.colegio_api.service.GradeLevelService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GradeLevelServiceImpl implements GradeLevelService {

    private final GradeLevelRepository gradeLevelRepository;
    private final AcademicPeriodRepository academicPeriodRepository;
    private final UserRepository userRepository;
    private final GroupMemberRepository groupMemberRepository;
    private final StudentRepository studentRepository;
    private final GradeLevelMapper gradeLevelMapper;
    private final StudentMapper studentMapper;

    @Override
    @Transactional(readOnly = true)
    public List<GradeLevelResponseDTO> findAll() {
        return gradeLevelRepository.findAllByOrderByNameAsc().stream().map(gl -> {
            GradeLevelResponseDTO dto = gradeLevelMapper.toResponseDTO(gl);
            dto.setMemberCount(groupMemberRepository.countByGroupId(gl.getId()));
            return dto;
        }).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public GradeLevelResponseDTO findById(Long id) {
        GradeLevel gl = gradeLevelRepository.findById(id)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "Grado no encontrado: " + id));
        GradeLevelResponseDTO dto = gradeLevelMapper.toResponseDTO(gl);
        dto.setMemberCount(groupMemberRepository.countByGroupId(id));
        return dto;
    }

    @Override
    @Transactional
    public GradeLevelResponseDTO create(GradeLevelRequestDTO dto) {
        GradeLevel gl = new GradeLevel();
        gl.setName(dto.getName().trim());
        gl.setSection(dto.getSection() != null ? dto.getSection().trim() : "");
        gl.setColor(dto.getColor());
        gl.setPhotoUrl(dto.getPhotoUrl());

        Long periodId = dto.getPeriodId();
        if (periodId == null) {
            periodId = academicPeriodRepository.findAll().stream()
                    .mapToLong(p -> p.getId()).max().orElse(1L);
        }
        AcademicPeriod period = academicPeriodRepository.findById(periodId)
                .orElseGet(() -> {
                    AcademicPeriod ap = new AcademicPeriod();
                    ap.setName("2025");
                    return academicPeriodRepository.save(ap);
                });
        gl.setPeriod(period);

        if (dto.getTutorId() != null) {
            gl.setTutor(userRepository.findById(dto.getTutorId()).orElse(null));
        }

        GradeLevel saved = gradeLevelRepository.save(gl);
        GradeLevelResponseDTO result = gradeLevelMapper.toResponseDTO(saved);
        result.setMemberCount(0L);
        return result;
    }

    @Override
    @Transactional
    public GradeLevelResponseDTO update(Long id, GradeLevelRequestDTO dto) {
        GradeLevel gl = gradeLevelRepository.findById(id)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "Grado no encontrado: " + id));

        if (dto.getName() != null) gl.setName(dto.getName().trim());
        if (dto.getSection() != null) gl.setSection(dto.getSection().trim());
        if (dto.getColor() != null) gl.setColor(dto.getColor());
        if (dto.getPhotoUrl() != null) gl.setPhotoUrl(dto.getPhotoUrl());
        if (dto.getPeriodId() != null) academicPeriodRepository.findById(dto.getPeriodId()).ifPresent(gl::setPeriod);
        gl.setTutor(dto.getTutorId() != null ? userRepository.findById(dto.getTutorId()).orElse(null) : null);

        GradeLevel saved = gradeLevelRepository.save(gl);
        GradeLevelResponseDTO result = gradeLevelMapper.toResponseDTO(saved);
        result.setMemberCount(groupMemberRepository.countByGroupId(id));
        return result;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        gradeLevelRepository.findById(id)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "Grado no encontrado: " + id));
        gradeLevelRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StudentResponseDTO> findMembers(Long groupId) {
        return groupMemberRepository.findByGroupId(groupId).stream()
                .map(gm -> studentMapper.toResponseDTO(gm.getStudent()))
                .toList();
    }

    @Override
    @Transactional
    public void addMember(Long groupId, Long studentId) {
        GradeLevel group = gradeLevelRepository.findById(groupId)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "Grupo no encontrado"));
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "Alumno no encontrado"));

        GroupMember gm = new GroupMember();
        gm.setGroup(group);
        gm.setStudent(student);
        groupMemberRepository.save(gm);
    }

    @Override
    @Transactional
    public void removeMember(Long groupId, Long studentId) {
        groupMemberRepository.deleteByGroupIdAndStudentId(groupId, studentId);
    }
}
