package project.service_implementors;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import project.dto.UserDTO;
import project.dto.requests.LoginRequest;
import project.dto.responses.ResponseObject;
import project.models.Token;
import project.models.TokenType;
import project.repositories.TokenDAO;
import project.repositories.UserDAO;
import project.services.AuthenticationService;
import project.services.JWTService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserDAO userDAO;
    private final TokenDAO tokenDAO;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;

    @Override
    public ResponseObject login(LoginRequest loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );
        var existedUser = userDAO.findUserDTOByEmail(loginRequest.getUsername()).orElse(null);
        String validToken = null;
        if (existedUser == null) {
            throw new UsernameNotFoundException("User not found");
        }
        List<Token> validTokens = tokenDAO.findAllValidTokenByUser(existedUser.getId());
        if (!validTokens.isEmpty()) {
            for (Token token : validTokens) {
                if (!jwtService.isTokenExpired(token.getToken())) {
                    validToken = token.getToken();
                } else {
                    token.setExpired(true);
                }
            }
            if (validToken == null) {
                tokenDAO.saveAll(validTokens);
            }
        } else {
            validToken = jwtService.generateToken(existedUser);
            revokeAllUserToken(existedUser.getId());
            saveUserToken(existedUser, validToken);
        }
        return ResponseObject.builder()
                .message("Success")
                .token(validToken)
                .info(existedUser)
                .build();
    }


    private void saveUserToken(UserDTO user, String token) {
        var saveUser = userDAO.findUserByEmail(user.getEmail()).orElse(null);
        if (saveUser != null) {
            Token userToken = Token.builder()
                    .token(token)
                    .tokenType(TokenType.builder()
                            .type("BEARER")
                            .build())
                    .expired(false)
                    .revoked(false)
                    .user(saveUser)
                    .build();
            tokenDAO.save(userToken);
        } else {
            throw new UsernameNotFoundException("Unknown User");
        }
    }

    private void revokeAllUserToken(int userId) {
        List<Token> tokenList = tokenDAO.findAllValidTokenByUser(userId);
        if (!tokenList.isEmpty()) {
            tokenList.forEach(token -> {
                token.setRevoked(true);
                token.setExpired(true);
            });
            tokenDAO.saveAll(tokenList);
        }
    }

}
