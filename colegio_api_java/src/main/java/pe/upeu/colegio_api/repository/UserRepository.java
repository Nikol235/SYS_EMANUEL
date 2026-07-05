package pe.edu.upeu.colegio_api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upeu.colegio_api.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    List<User> findByRoleAndActiveTrue(String role);
    Page<User> findAll(Pageable pageable);
    List<User> findByRoleInAndActiveTrue(List<String> roles);
}
