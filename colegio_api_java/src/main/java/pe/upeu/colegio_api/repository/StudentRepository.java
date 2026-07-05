package pe.edu.upeu.colegio_api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pe.edu.upeu.colegio_api.entity.Student;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {

    @Query("SELECT s FROM Student s WHERE s.active = true ORDER BY s.lastName, s.firstName")
    List<Student> findAllActive();

    @Query("SELECT s FROM Student s JOIN ParentStudent ps ON ps.student.id = s.id WHERE ps.parent.id = :parentId AND s.active = true")
    List<Student> findByParentId(@Param("parentId") Long parentId);

    @Query("""
        SELECT DISTINCT s FROM Student s
        JOIN s.gradeLevel gl
        JOIN TeacherCourse tc ON tc.gradeLevel.id = gl.id
        WHERE tc.teacher.id = :teacherId AND s.active = true
        """)
    List<Student> findByTeacherId(@Param("teacherId") Long teacherId);

    Page<Student> findAll(Pageable pageable);

    List<Student> findByActiveTrueOrderByLastNameAscFirstNameAsc();

    @Query("SELECT COUNT(s) FROM Student s WHERE s.active = true")
    long countActive();
}
