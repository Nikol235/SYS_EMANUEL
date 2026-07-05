package pe.edu.upeu.colegio_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pe.edu.upeu.colegio_api.entity.ParentStudent;

import java.util.List;

public interface ParentStudentRepository extends JpaRepository<ParentStudent, Long> {

    List<ParentStudent> findByStudentId(Long studentId);

    @Query("SELECT ps.parent.id FROM ParentStudent ps WHERE ps.student.id = :studentId")
    List<Long> findParentIdsByStudentId(@Param("studentId") Long studentId);

    void deleteByStudentId(Long studentId);

    boolean existsByParentId(Long parentId);
}
