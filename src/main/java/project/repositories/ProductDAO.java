package project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import project.models.Product;

import java.util.List;

@Repository
public interface ProductDAO extends JpaRepository<Product, Integer> {

    @Query("""
                SELECT p FROM Product AS p
                WHERE p.isAvailable = TRUE
            """)
    List<Product> getAllAvailableProducts();
}
