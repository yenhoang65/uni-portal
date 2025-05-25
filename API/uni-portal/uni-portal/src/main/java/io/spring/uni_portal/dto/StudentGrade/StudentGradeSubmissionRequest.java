package io.spring.uni_portal.dto.StudentGrade;

public class StudentGradeSubmissionRequest {

    private Long classStudentId; // Thay classSubjectStudentId bằng classStudentId
    private Long gradeEventId;
    private String fileUrl;

    // Getters and Setters
    public Long getClassStudentId() {
        return classStudentId;
    }

    public void setClassStudentId(Long classStudentId) {
        this.classStudentId = classStudentId;
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
}
