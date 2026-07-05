package pe.edu.upeu.colegio_api.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upeu.colegio_api.dto.TeacherCourseRequestDTO;
import pe.edu.upeu.colegio_api.dto.TeacherCourseResponseDTO;
import pe.edu.upeu.colegio_api.entity.AcademicPeriod;
import pe.edu.upeu.colegio_api.entity.TeacherCourse;
import pe.edu.upeu.colegio_api.exception.AppException;
import pe.edu.upeu.colegio_api.mapper.TeacherCourseMapper;
import pe.edu.upeu.colegio_api.repository.*;
import pe.edu.upeu.colegio_api.service.TeacherCourseService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeacherCourseServiceImpl implements TeacherCourseService {

    private final TeacherCourseRepository teacherCourseRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final GradeLevelRepository gradeLevelRepository;
    private final AcademicPeriodRepository academicPeriodRepository;
    private final GradeRepository gradeRepository;
    private final DailyProgressRepository dailyProgressRepository;
    private final TeacherCourseMapper teacherCourseMapper;

    @Override
    @Transactional(readOnly = true)
    public List<TeacherCourseResponseDTO> findAll(String username, String role) {
        List<TeacherCourse> list;
        if ("docente".equals(role)) {
            Long teacherId = userRepository.findByUsername(username).map(u -> u.getId()).orElseThrow();
            list = teacherCourseRepository.findByTeacherId(teacherId);
        } else if ("padre".equals(role)) {
            Long parentId = userRepository.findByUsername(username).map(u -> u.getId()).orElseThrow();
            list = teacherCourseRepository.findByParentId(parentId);
        } else {
            list = teacherCourseRepository.findAll();
        }
        return list.stream().map(teacherCourseMapper::toResponseDTO).toList();
    }

    @Override
    @Transactional
    public TeacherCourseResponseDTO create(TeacherCourseRequestDTO dto) {
        TeacherCourse tc = new TeacherCourse();
        tc.setTeacher(userRepository.findById(dto.getTeacherId())
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "Docente no encontrado")));
        tc.setCourse(courseRepository.findById(dto.getCourseId())
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "Curso no encontrado")));
        tc.setGradeLevel(gradeLevelRepository.findById(dto.getGradeLevelId())
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "Grado no encontrado")));
        if (dto.getPeriodId() != null) {
            AcademicPeriod period = academicPeriodRepository.findById(dto.getPeriodId())
                    .orElseGet(() -> academicPeriodRepository.findAll().stream().findFirst().orElseThrow());
            tc.setPeriod(period);
        } else {
            academicPeriodRepository.findAll().stream().findFirst().ifPresent(tc::setPeriod);
        }
        return teacherCourseMapper.toResponseDTO(teacherCourseRepository.save(tc));
    }

    @Override
    @Transactional
    public TeacherCourseResponseDTO updateEvalNames(Long id, List<String> evalNames, String username, String role) {
        TeacherCourse tc = teacherCourseRepository.findById(id)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "Asignación no encontrada: " + id));
        if ("docente".equals(role)) {
            Long teacherId = userRepository.findByUsername(username).map(u -> u.getId()).orElseThrow();
            if (!tc.getTeacher().getId().equals(teacherId)) {
                throw new AppException(HttpStatus.FORBIDDEN, "No autorizado");
            }
        }
        tc.setEvalNames("[\"" + String.join("\",\"", evalNames) + "\"]");
        return teacherCourseMapper.toResponseDTO(teacherCourseRepository.save(tc));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        TeacherCourse tc = teacherCourseRepository.findById(id)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "Asignación no encontrada: " + id));
        gradeRepository.findByTeacherCourseId(id).forEach(g -> gradeRepository.delete(g));
        dailyProgressRepository.findByTeacherCourseIdOrderByDateDesc(id).forEach(dp -> dailyProgressRepository.delete(dp));
        teacherCourseRepository.delete(tc);
    }
}
