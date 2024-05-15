package project.configurations;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.w3c.dom.ls.LSResourceResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import project.models.Permission;
import project.models.Role;
import project.models.TokenType;
import project.models.User;
import project.repositories.PermissionDAO;
import project.repositories.RoleDAO;
import project.repositories.TokenTypeDAO;
import project.repositories.UserDAO;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.validation.Validator;
import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfiguration {

    private final PermissionDAO permissionDAO;
    private final RoleDAO roleDAO;
    private final TokenTypeDAO tokenTypeDAO;
    private final UserDAO userDAO;

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> userDAO.findUserByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("No user found!"));
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenConfig) throws Exception {
        return authenConfig.getAuthenticationManager();
    }

    @Bean
    public Validator validator() {
        return new Validator() {
            @Override
            public void reset() {

            }

            @Override
            public void validate(Source source, Result result) throws SAXException, IOException {

            }

            @Override
            public void setErrorHandler(ErrorHandler errorHandler) {

            }

            @Override
            public ErrorHandler getErrorHandler() {
                return null;
            }

            @Override
            public void setResourceResolver(LSResourceResolver resourceResolver) {

            }

            @Override
            public LSResourceResolver getResourceResolver() {
                return null;
            }
        };
    }
}
