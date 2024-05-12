package project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.models.Permission;

@Repository
public interface PermissionDAO extends JpaRepository<Permission, Integer> {
}
