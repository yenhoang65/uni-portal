package io.spring.uni_portal.dto.ClassSubjectStudent;

import io.spring.uni_portal.model.ClassSubjectStudent;

public class ClassSubjectStudentDTO {
    private Long id;
    private Long classStudentId;
    private Long studentId;
    private String status;

    public ClassSubjectStudentDTO(ClassSubjectStudent entity) {
        this.id = entity.getClassSubjectStudentId();
        this.classStudentId = entity.getClassStudent().getClassStudentId();
        this.studentId = entity.getStudent().getUserId();
        this.status = entity.getStatus();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getClassStudentId() {
        return classStudentId;
    }

    public void setClassStudentId(Long classStudentId) {
        this.classStudentId = classStudentId;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

