package pe.edu.upeu.colegio_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pe.edu.upeu.colegio_api.entity.TeacherCourse;

import java.util.List;

public interface TeacherCourseRepository extends JpaRepository<TeacherCourse, Long> {

    List<TeacherCourse> findByTeacherId(Long teacherId);

    List<TeacherCourse> findByGradeLevelId(Long gradeLevelId);

    @Query("SELECT tc FROM TeacherCourse tc JOIN tc.teacher t JOIN ParentStudent ps ON ps.student.gradeLevel.id = tc.gradeLevel.id WHERE ps.parent.id = :parentId")
    List<TeacherCourse> findByParentId(@Param("parentId") Long parentId);

    @Query("SELECT COUNT(tc) FROM TeacherCourse tc WHERE tc.teacher.id = :teacherId")
    long countByTeacherId(@Param("teacherId") Long teacherId);
}
