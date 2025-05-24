package io.spring.uni_portal.dto.StudentGrade;

import java.time.LocalDateTime;

public class StudentGradeSubmissionResponse {
    private Long studentGradeId;
    private Long classSubjectStudentId;
    private Long gradeEventId;
    private String fileUrl;
    private LocalDateTime submittedAt;

    // Getters and Setters
    public Long getStudentGradeId() {
        return studentGradeId;
    }

    public void setStudentGradeId(Long studentGradeId) {
        this.studentGradeId = studentGradeId;
    }

    public Long getClassSubjectStudentId() {
        return classSubjectStudentId;
    }

    public void setClassSubjectStudentId(Long classSubjectStudentId) {
        this.classSubjectStudentId = classSubjectStudentId;
    }

    public Long getGradeEventId() {
        return gradeEventId;
    }

    public void setGradeEventId(Long gradeEventId) {
        this.gradeEventId = gradeEventId;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public LocalDateTime getSubmittedAt() {
        return submittedAt;
    }

    public void setSubmittedAt(LocalDateTime submittedAt) {
        this.submittedAt = submittedAt;
    }
}
