package project.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.dto.requests.LoginRequest;
import project.dto.responses.ResponseObject;
import project.services.AuthenticationService;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "authenticate")
@EnableMethodSecurity
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @GetMapping("login")
    public ResponseEntity<ResponseObject> login(@RequestBody @Valid LoginRequest loginRequest) {
        try {
            ResponseObject responseObject = authenticationService.login(loginRequest);
            return ResponseEntity.ok(responseObject);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ResponseObject.builder()
                    .message(e.getMessage())
                    .build());
        }
    }

    @GetMapping("test")
    @PreAuthorize("hasAuthority('user:read')")
    public String testQuote() {
        return "Successfully access";
    }
}
