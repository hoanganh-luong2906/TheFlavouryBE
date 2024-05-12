package project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.models.TokenType;

@Repository
public interface TokenTypeDAO extends JpaRepository<TokenType, Integer> {
}
