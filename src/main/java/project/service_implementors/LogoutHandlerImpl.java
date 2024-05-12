package project.service_implementors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;
import project.repositories.TokenDAO;

@Service
@RequiredArgsConstructor
public class LogoutHandlerImpl implements LogoutHandler {

    private final TokenDAO tokenDAO;

    /**
     * @param request logout request
     * @param response logout response (no response)
     * @param authentication
     * Process the request, check existence in the database then revoke it.
     */
    @Override
    public void logout(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) {
        String authenHeader = request.getHeader("Authorization");
        if (authenHeader != null && authenHeader.startsWith("Bearer ")) {
            String token = authenHeader.substring(7);
            var existedToken = tokenDAO.findByToken(token).orElse(null);
            if (existedToken != null) {
                existedToken.setExpired(true);
                existedToken.setRevoked(true);
                tokenDAO.save(existedToken);
            }
        }
    }

}
