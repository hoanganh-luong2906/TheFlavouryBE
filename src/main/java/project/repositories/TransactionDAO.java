package project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.models.Transaction;

@Repository
public interface TransactionDAO extends JpaRepository<Transaction, Integer> {
}
