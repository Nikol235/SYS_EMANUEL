package pe.edu.upeu.colegio_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upeu.colegio_api.entity.Schedule;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    List<Schedule> findByGradeLevelIdOrderByStartTimeAscDayOfWeekAsc(Long gradeLevelId);

    List<Schedule> findAllByOrderByStartTimeAscDayOfWeekAsc();
}
