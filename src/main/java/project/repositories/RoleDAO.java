package project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.models.Role;

@Repository
public interface RoleDAO extends JpaRepository<Role, Integer> {
}
