package pe.edu.upeu.colegio_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pe.edu.upeu.colegio_api.entity.Attendance;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    List<Attendance> findByStudentIdOrderByDateDesc(Long studentId);

    Optional<Attendance> findByStudentIdAndDateAndTurnoAndTipo(
            Long studentId, LocalDate date, String turno, String tipo);

    @Query("SELECT a FROM Attendance a WHERE a.student.id = :studentId AND a.date BETWEEN :from AND :to ORDER BY a.date DESC")
    List<Attendance> findByStudentIdAndDateRange(@Param("studentId") Long studentId,
                                                  @Param("from") LocalDate from,
                                                  @Param("to") LocalDate to);

    void deleteByStudentIdAndDate(Long studentId, LocalDate date);
}
