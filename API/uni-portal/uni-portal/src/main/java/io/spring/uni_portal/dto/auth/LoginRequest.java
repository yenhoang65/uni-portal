package io.spring.uni_portal.dto.auth;

public class LoginRequest {
    private Long userId;
    private String password;

    public LoginRequest() {}

    public Long getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

