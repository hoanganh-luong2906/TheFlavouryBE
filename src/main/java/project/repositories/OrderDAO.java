package project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.models.Order;

@Repository
public interface OrderDAO extends JpaRepository<Order, Integer> {
}
