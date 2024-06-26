package project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.models.OrderItem;

@Repository
public interface OrderItemDAO extends JpaRepository<OrderItem, Integer> {
}
