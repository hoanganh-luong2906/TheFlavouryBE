package project.services;

import project.dto.requests.LoginRequest;
import project.dto.responses.ResponseObject;

public interface AuthenticationService {

    ResponseObject login(LoginRequest loginRequest);

}
