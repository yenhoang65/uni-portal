package io.spring.uni_portal.security;

import org.springframework.context.annotation.*;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(auth -> auth
                        // Cho phép Swagger UI và API docs mà không cần xác thực
                        .requestMatchers(
                                "/swagger-ui.html",
                                "/swagger-ui/**",
                                "/v3/api-docs/**"
                        ).permitAll()
                        .requestMatchers(
                                "/api/auth/**",
                                "/api/faculties/**",
                                "/api/majors/**",
                                "/api/lecturers/**",
                                "/api/classrooms/**",
                                "/api/specializations/**",
                                "/api/subjects/**",
                                "/api/training-programs/**",
                                "/api/intermediaries/**",
                                "/api/classes/**",
                                "/api/students/**",
                                "/api/registration-period/**",
                                "/api/teaching-assignment/**",
                                "/api/term-classes/**",
                                "/api/teaching-schedule/**",
                                "/api/user/**",
                                "/api/class-student/**",
                                "/api/class-registration/**",
                                "/api/class-subject-student/**",
                                "/api/attendance/**",
                                "/api/exam-period/**",
                                "/api/grade-types/**",
                                "/api/exam-schedule/**",
                                "/api/grade-event/**",
                                "/api/student-grade/**"
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
