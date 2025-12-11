package io.spring.uni_portal.dto.ClassSubjectStudent;

public class ClassStudentSubjectClassDTO {

    private Long classStudentId;
    private Long subjectId;
    private String subjectName;

    public ClassStudentSubjectClassDTO(Long classStudentId, Long subjectId, String subjectName) {
        this.classStudentId = classStudentId;
        this.subjectId = subjectId;
        this.subjectName = subjectName;
    }

    // Getters & setters
    public Long getClassStudentId() {
        return classStudentId;
    }

    public void setClassStudentId(Long classStudentId) {
        this.classStudentId = classStudentId;
    }

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }
}
