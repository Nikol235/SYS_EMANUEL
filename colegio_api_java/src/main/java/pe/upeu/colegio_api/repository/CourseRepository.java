package pe.edu.upeu.colegio_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upeu.colegio_api.entity.Course;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findAllByOrderByNameAsc();
}
