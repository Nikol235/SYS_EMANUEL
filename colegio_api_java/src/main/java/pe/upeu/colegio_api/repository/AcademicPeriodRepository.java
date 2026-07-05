package pe.edu.upeu.colegio_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upeu.colegio_api.entity.AcademicPeriod;

public interface AcademicPeriodRepository extends JpaRepository<AcademicPeriod, Long> {
}
