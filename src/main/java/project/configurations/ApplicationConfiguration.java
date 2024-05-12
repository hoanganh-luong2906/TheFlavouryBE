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
import project.models.Permission;
import project.models.Role;
import project.models.TokenType;
import project.models.User;
import project.repositories.PermissionDAO;
import project.repositories.RoleDAO;
import project.repositories.TokenTypeDAO;
import project.repositories.UserDAO;

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
    public CommandLineRunner initData() {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                TokenType tokenType;
                Role role = Role.builder().build();
                if (tokenTypeDAO.findAll().isEmpty()) {
                    tokenType = TokenType.builder()
                            .type("Bearer")
                            .build();
                    tokenTypeDAO.save(tokenType);
                }
                if (permissionDAO.findAll().isEmpty() && roleDAO.findAll().isEmpty()) {
                    role.setRole("ADMIN");
                    Set<Permission> permissions = new HashSet<>();
                    for (int i = 0; i < 4; i++) {
                        List<String> permissionList =
                                List.of("user:read", "user:create", "user:update", "user:delete");
                        var permission = Permission.builder()
                                .permission(permissionList.get(i))
                                .build();
                        permissions.add(permission);
                    }
                    role.setPermissions(permissions);
                    permissionDAO.saveAll(permissions);
                    roleDAO.save(role);
                }
                if (userDAO.findAll().isEmpty()) {
                    User user = User.builder()
                            .name("Hoang Anh")
                            .email("admin@gmail.com")
                            .password(passwordEncoder().encode("1"))
                            .role(role)
                            .dob(new Date())
                            .gender("Male")
                            .actived(true)
                            .build();
                    userDAO.save(user);
                }
            }
        };
    }
}
