package pe.edu.upeu.colegio_api.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upeu.colegio_api.dto.GradeRequestDTO;
import pe.edu.upeu.colegio_api.dto.GradeResponseDTO;
import pe.edu.upeu.colegio_api.entity.Grade;
import pe.edu.upeu.colegio_api.exception.AppException;
import pe.edu.upeu.colegio_api.mapper.GradeMapper;
import pe.edu.upeu.colegio_api.repository.*;
import pe.edu.upeu.colegio_api.service.GradeService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GradeServiceImpl implements GradeService {

    private final GradeRepository gradeRepository;
    private final StudentRepository studentRepository;
    private final TeacherCourseRepository teacherCourseRepository;
    private final UserRepository userRepository;
    private final GradeMapper gradeMapper;

    @Override
    @Transactional(readOnly = true)
    public List<GradeResponseDTO> findAll(Long studentId, Long teacherCourseId, String username, String role) {
        List<Grade> grades;
        if (studentId != null && teacherCourseId != null) {
            grades = gradeRepository.findByStudentId(studentId).stream()
                    .filter(g -> g.getTeacherCourse().getId().equals(teacherCourseId))
                    .toList();
        } else if (studentId != null) {
            grades = gradeRepository.findByStudentId(studentId);
        } else if (teacherCourseId != null) {
            grades = gradeRepository.findByTeacherCourseId(teacherCourseId);
        } else {
            grades = gradeRepository.findAll();
        }
        return grades.stream().map(gradeMapper::toResponseDTO).toList();
    }

    @Override
    @Transactional
    public GradeResponseDTO saveOrUpdate(GradeRequestDTO dto, String username, String role) {
        var tc = teacherCourseRepository.findById(dto.getTeacherCourseId())
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "Asignación no encontrada"));

        if ("docente".equals(role)) {
            Long teacherId = userRepository.findByUsername(username).map(u -> u.getId()).orElseThrow();
            if (!tc.getTeacher().getId().equals(teacherId)) {
                throw new AppException(HttpStatus.FORBIDDEN, "No autorizado para este curso");
            }
        }

        Grade grade = gradeRepository.findByStudentIdAndTeacherCourseIdAndEvaluationName(
                dto.getStudentId(), dto.getTeacherCourseId(), dto.getEvaluationName()
        ).orElseGet(() -> {
            Grade g = new Grade();
            g.setStudent(studentRepository.findById(dto.getStudentId()).orElseThrow());
            g.setTeacherCourse(tc);
            g.setEvaluationName(dto.getEvaluationName());
            return g;
        });

        grade.setScore(dto.getScore());
        return gradeMapper.toResponseDTO(gradeRepository.save(grade));
    }

    @Override
    @Transactional
    public GradeResponseDTO update(Long id, Double score) {
        Grade grade = gradeRepository.findById(id)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "Nota no encontrada: " + id));
        grade.setScore(score);
        return gradeMapper.toResponseDTO(gradeRepository.save(grade));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        gradeRepository.findById(id)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "Nota no encontrada: " + id));
        gradeRepository.deleteById(id);
    }
}
