package io.spring.uni_portal.service.authService;
import io.spring.uni_portal.dto.auth.LoginRequest;
import io.spring.uni_portal.dto.auth.LoginResponse;

public interface IAuthService {
    LoginResponse login(LoginRequest request);
}
