package pe.edu.upeu.colegio_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upeu.colegio_api.entity.GradeLevel;

import java.util.List;
import java.util.Optional;

public interface GradeLevelRepository extends JpaRepository<GradeLevel, Long> {
    List<GradeLevel> findAllByOrderByNameAsc();
    Optional<GradeLevel> findByName(String name);
}
