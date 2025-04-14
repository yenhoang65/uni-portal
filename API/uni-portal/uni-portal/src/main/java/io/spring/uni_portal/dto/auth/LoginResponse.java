package io.spring.uni_portal.dto.auth;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginResponse {
    @JsonProperty("token")
    private String token;

    @JsonProperty("role")
    private String role;

    public LoginResponse(String token, String role) {
        this.token = token;
        this.role = role;
    }
    // Getters
}
