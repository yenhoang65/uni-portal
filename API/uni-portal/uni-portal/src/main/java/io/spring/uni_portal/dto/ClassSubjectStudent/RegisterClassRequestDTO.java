package io.spring.uni_portal.dto.ClassSubjectStudent;

public class RegisterClassRequestDTO {

    private Long classStudentId;
    private String status;

    public Long getClassStudentId() {
        return classStudentId;
    }

    public void setClassStudentId(Long classStudentId) {
        this.classStudentId = classStudentId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
