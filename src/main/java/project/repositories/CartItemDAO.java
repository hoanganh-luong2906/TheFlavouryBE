package project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.models.CartItem;

@Repository
public interface CartItemDAO extends JpaRepository<CartItem, Integer> {
}
