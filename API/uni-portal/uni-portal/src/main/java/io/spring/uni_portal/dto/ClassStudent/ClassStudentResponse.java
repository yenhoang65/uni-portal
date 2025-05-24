package io.spring.uni_portal.dto.ClassStudent;

import java.time.LocalDateTime;

public class ClassStudentResponse {
    private Long studentId;
    private String name;
    private String code;
    private Long classSubjectStudentId;
    private LocalDateTime registrationTime;
    private String status;
    private Long classStudentId;

    // Getters and Setters
    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getClassSubjectStudentId() {
        return classSubjectStudentId;
    }

    public void setClassSubjectStudentId(Long classSubjectStudentId) {
        this.classSubjectStudentId = classSubjectStudentId;
    }

    public LocalDateTime getRegistrationTime() {
        return registrationTime;
    }

    public void setRegistrationTime(LocalDateTime registrationTime) {
        this.registrationTime = registrationTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getClassStudentId() {
        return classStudentId;
    }

    public void setClassStudentId(Long classStudentId) {
        this.classStudentId = classStudentId;
    }
}
