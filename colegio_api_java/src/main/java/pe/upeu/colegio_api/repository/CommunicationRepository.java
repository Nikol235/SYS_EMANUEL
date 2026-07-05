package pe.edu.upeu.colegio_api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pe.edu.upeu.colegio_api.entity.Communication;

public interface CommunicationRepository extends JpaRepository<Communication, Long> {

    @Query("SELECT c FROM Communication c ORDER BY c.createdAt DESC")
    Page<Communication> findAllOrderByCreatedAtDesc(Pageable pageable);

    @Query("SELECT c FROM Communication c WHERE c.type = 'general' OR c.gradeLevel.id IN :gradeLevelIds ORDER BY c.createdAt DESC")
    Page<Communication> findForParent(@Param("gradeLevelIds") java.util.List<Long> gradeLevelIds, Pageable pageable);

    @Query("SELECT c FROM Communication c WHERE c.author.id = :authorId OR c.type = 'general' OR c.type = 'grado' ORDER BY c.createdAt DESC")
    Page<Communication> findForTeacher(@Param("authorId") Long authorId, Pageable pageable);
}
