package pe.edu.upeu.colegio_api.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upeu.colegio_api.dto.DashboardAdminDTO;
import pe.edu.upeu.colegio_api.repository.*;
import pe.edu.upeu.colegio_api.service.DashboardService;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final PaymentRepository paymentRepository;
    private final TeacherCourseRepository teacherCourseRepository;
    private final GradeRepository gradeRepository;

    @Override
    @Transactional(readOnly = true)
    public DashboardAdminDTO getAdminDashboard() {
        long totalTeachers = userRepository.findByRoleAndActiveTrue("docente").size();
        long totalStudents = studentRepository.countActive();
        long totalCourses = courseRepository.count();
        long pendingPayments = paymentRepository.countPending();
        var pendingAmount = paymentRepository.sumPendingAmount();
        return new DashboardAdminDTO(totalTeachers, totalStudents, totalCourses, pendingPayments, pendingAmount);
    }

    @Override
    @Transactional(readOnly = true)
    public Map<String, Object> getDocenteDashboard(String username) {
        Long teacherId = userRepository.findByUsername(username).map(u -> u.getId()).orElseThrow();
        var courses = teacherCourseRepository.findByTeacherId(teacherId);
        long totalStudents = studentRepository.findByTeacherId(teacherId).size();

        Map<String, Object> result = new HashMap<>();
        result.put("totalCourses", courses.size());
        result.put("totalStudents", totalStudents);
        result.put("courses", courses.stream().map(tc -> {
            Map<String, Object> c = new HashMap<>();
            c.put("id", tc.getId());
            c.put("name", tc.getCourse().getName());
            c.put("color", tc.getCourse().getColor());
            c.put("gradeName", tc.getGradeLevel().getName());
            c.put("section", tc.getGradeLevel().getSection());
            return c;
        }).toList());
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Map<String, Object> getPadreDashboard(String username) {
        Long parentId = userRepository.findByUsername(username).map(u -> u.getId()).orElseThrow();
        var students = studentRepository.findByParentId(parentId);

        Map<String, Object> result = new HashMap<>();
        if (students.isEmpty()) {
            result.put("student", null);
            result.put("stats", Map.of());
            return result;
        }

        var student = students.get(0);
        double avgScore = gradeRepository.avgScoreByStudentId(student.getId()) != null
                ? gradeRepository.avgScoreByStudentId(student.getId()) : 0.0;
        long totalGrades = gradeRepository.countByStudentId(student.getId());
        long pendingPayments = paymentRepository.findByStudentIdAndPaidFalse(student.getId()).size();

        Map<String, Object> studentInfo = new HashMap<>();
        studentInfo.put("name", student.getFirstName() + " " + student.getLastName());
        studentInfo.put("grade", student.getGradeLevel().getName());
        studentInfo.put("section", student.getGradeLevel().getSection());
        studentInfo.put("codigo", student.getCodigo());

        Map<String, Object> stats = new HashMap<>();
        stats.put("promedio", String.format("%.1f", avgScore));
        stats.put("totalNotas", totalGrades);
        stats.put("pagosPendientes", pendingPayments);

        result.put("student", studentInfo);
        result.put("stats", stats);
        return result;
    }
}
