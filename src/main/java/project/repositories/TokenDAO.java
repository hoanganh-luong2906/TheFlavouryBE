package project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import project.models.Token;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenDAO extends JpaRepository<Token, Integer> {

    @Query("""
            SELECT t FROM Token AS t 
            INNER JOIN User AS u ON t.user.id = u.id 
            WHERE u.id = :userId AND (t.revoked = FALSE OR t.expired = FALSE)
            """)
    List<Token> findAllValidTokenByUser(Integer userId);

    Optional<Token> findByToken(String token);
}
