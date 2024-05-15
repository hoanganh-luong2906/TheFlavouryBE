package project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.models.Cart;

@Repository
public interface CartDAO extends JpaRepository<Cart, Integer> {
}
