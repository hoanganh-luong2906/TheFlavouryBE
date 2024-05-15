package project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.models.ProductMeta;

@Repository
public interface ProductMetaDAO extends JpaRepository<ProductMeta, Integer> {
}
