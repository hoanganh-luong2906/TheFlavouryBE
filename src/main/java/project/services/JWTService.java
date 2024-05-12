package project.services;

import org.springframework.security.core.userdetails.UserDetails;
import project.dto.UserDTO;

public interface JWTService {

    String extractEmail(String token);

    String generateToken(UserDTO userDTO);

    String generateRefreshToken(UserDetails userDetails);

    boolean isTokenValid(String token, UserDetails userDetails);

    boolean isTokenExpired(String token);
}
