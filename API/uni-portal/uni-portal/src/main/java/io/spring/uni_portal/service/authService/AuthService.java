package io.spring.uni_portal.service.authService;

import io.spring.uni_portal.dto.auth.LoginRequest;
import io.spring.uni_portal.dto.auth.LoginResponse;
import io.spring.uni_portal.exception.InvalidPasswordException;
import io.spring.uni_portal.exception.UserNotFoundException;
import io.spring.uni_portal.model.User;
import io.spring.uni_portal.repository.UserRepository;
import io.spring.uni_portal.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements IAuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new UserNotFoundException("Tài khoản không tồn tại"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidPasswordException("Mật khẩu không đúng");
        }

        String token = jwtService.generateToken(user);
        return new LoginResponse(token, user.getRole());
    }
}
