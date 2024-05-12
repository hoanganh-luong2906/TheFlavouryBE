package project.service_implementors;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import project.dto.UserDTO;
import project.repositories.TokenDAO;
import project.repositories.UserDAO;
import project.services.JWTService;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JWTServiceImpl implements JWTService {

    private final TokenDAO tokenDAO;
    private final UserDAO userDAO;

    @Value("${spring.jwt.security.secret-key}")
    private String secretKey;

    @Value("${spring.jwt.security.expiration}")
    private int jwtExpiration;

    @Value("${spring.jwt.security.refresh-token.expiration}")
    private int refreshExpiration;

    @Override
    public String extractEmail(String token) {
        return extractClaimsFromToken(token, Claims::getSubject);
    }

    private <T> T extractClaimsFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigninKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSigninKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    @Override
    public String generateToken(UserDTO userDTO) {
        var user = userDAO.findUserByEmail(userDTO.getEmail()).orElse(null);
        return generateToken(new HashMap<>(), user);
    }

    private String generateToken(HashMap<String, Object> extraClaims, UserDetails userDetails) {
        return buildToken(extraClaims, userDetails, jwtExpiration);
    }

    private String buildToken(HashMap<String, Object> extraClaims, UserDetails userDetails, int expiration) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSigninKey())
                .compact();
    }

    @Override
    public String generateRefreshToken(UserDetails userDetails) {
        return buildToken(new HashMap<>(), userDetails, refreshExpiration);
    }

    @Override
    public boolean isTokenValid(String token, UserDetails userDetails) {
        String userEmail = userDetails.getUsername();
        var validToken = tokenDAO.findByToken(token)
                .map(t -> !t.isExpired() && !t.isRevoked()).orElse(false);
        return (
                userEmail.equals(extractEmail(token)) &&
                        !isTokenExpired(token) &&
                        validToken
        );
    }

    @Override
    public boolean isTokenExpired(String token) {
        return extractExpirationFromToken(token).before(new Date());
    }

    private Date extractExpirationFromToken(String token) {
        return extractClaimsFromToken(token, Claims::getExpiration);
    }
}
