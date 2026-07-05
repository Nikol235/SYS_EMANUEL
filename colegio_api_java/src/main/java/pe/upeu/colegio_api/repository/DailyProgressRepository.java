package pe.edu.upeu.colegio_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upeu.colegio_api.entity.DailyProgress;

import java.time.LocalDate;
import java.util.List;

public interface DailyProgressRepository extends JpaRepository<DailyProgress, Long> {

    List<DailyProgress> findByTeacherCourseIdOrderByDateDesc(Long teacherCourseId);

    List<DailyProgress> findByTeacherCourseIdAndDate(Long teacherCourseId, LocalDate date);
}
