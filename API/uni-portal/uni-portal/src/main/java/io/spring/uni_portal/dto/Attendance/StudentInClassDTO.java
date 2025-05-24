package io.spring.uni_portal.dto.Attendance;

public class StudentInClassDTO {
    private Long classSubjectStudentId;
    private Long userId;
    private String fullName; // nếu bạn có field này từ entity User
    private String status;

    // Getters and Setters
    public Long getClassSubjectStudentId() {
        return classSubjectStudentId;
    }

    public void setClassSubjectStudentId(Long classSubjectStudentId) {
        this.classSubjectStudentId = classSubjectStudentId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
