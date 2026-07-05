package pe.edu.upeu.colegio_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pe.edu.upeu.colegio_api.entity.Grade;

import java.util.List;
import java.util.Optional;

public interface GradeRepository extends JpaRepository<Grade, Long> {

    List<Grade> findByStudentId(Long studentId);

    List<Grade> findByTeacherCourseId(Long teacherCourseId);

    @Query("SELECT g FROM Grade g WHERE g.teacherCourse.teacher.id = :teacherId")
    List<Grade> findByTeacherCourseTeacherId(@Param("teacherId") Long teacherId);

    Optional<Grade> findByStudentIdAndTeacherCourseIdAndEvaluationName(
            Long studentId, Long teacherCourseId, String evaluationName);

    @Query("SELECT AVG(g.score) FROM Grade g WHERE g.student.id = :studentId")
    Double avgScoreByStudentId(@Param("studentId") Long studentId);

    @Query("SELECT COUNT(g) FROM Grade g WHERE g.student.id = :studentId")
    long countByStudentId(@Param("studentId") Long studentId);
}
