package pe.edu.upeu.colegio_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upeu.colegio_api.entity.GroupMember;

import java.util.List;

public interface GroupMemberRepository extends JpaRepository<GroupMember, Long> {

    List<GroupMember> findByGroupId(Long groupId);

    void deleteByGroupIdAndStudentId(Long groupId, Long studentId);

    long countByGroupId(Long groupId);
}
