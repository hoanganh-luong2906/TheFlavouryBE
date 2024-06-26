package project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.models.Category;

@Repository
public interface CategoryDAO extends JpaRepository<Category, Integer> {
}
