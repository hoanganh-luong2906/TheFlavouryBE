package project.configurations;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import project.services.JWTService;

import java.util.Objects;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Log4j2
public class AuditorAwareImpl implements AuditorAware<String> {
    private final JWTService jwtService;

    @Override
    public Optional<String> getCurrentAuditor() {
        try {
            String authHeader
                    = (Objects.requireNonNull((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()))
                    .getRequest().getHeader("Authorization");
            String token = authHeader.substring(7);
            String username = jwtService.extractEmail(token);
            return Optional.ofNullable(username);
        } catch (NullPointerException e) {
            log.error("Error: " + e.getMessage());
        }
        return Optional.empty();
    }
}
