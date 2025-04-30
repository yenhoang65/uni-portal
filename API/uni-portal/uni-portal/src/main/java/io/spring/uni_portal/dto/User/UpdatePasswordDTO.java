package io.spring.uni_portal.dto.User;

public class UpdatePasswordDTO {

    private String password; // Mật khẩu hiện tại
    private String newPassword; // Mật khẩu mới

    // Constructors, Getters, Setters
    public UpdatePasswordDTO() {}

    public UpdatePasswordDTO(String password, String newPassword) {
        this.password = password;
        this.newPassword = newPassword;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
