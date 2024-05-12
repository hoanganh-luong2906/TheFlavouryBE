package project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import project.dto.UserDTO;
import project.models.User;

import java.util.Optional;

@Repository
public interface UserDAO extends JpaRepository<User, Integer> {

    Optional<User> findUserByEmail(String email);

    @Query("""
            SELECT U.id AS id, U.email AS email, 
            U.name AS name, U.dob AS dob, U.gender AS gender, 
            U.createdBy AS createdBy, U.createdDate AS createdDate, 
            U.modifiedBy AS modifiedBy, U.modifiedDate AS modifiedDate
            FROM  User AS U
            WHERE U.email = :email
                """)
    Optional<UserDTO> findUserDTOByEmail(String email);
}
