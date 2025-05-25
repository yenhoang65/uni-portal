package io.spring.uni_portal.dto.ClassStudent;

public class ClassStudentRequestDTO {
    private Long classStudentId;
    private String className;
    private String subjectName;

    // Constructors
    public ClassStudentRequestDTO() {
    }

    public ClassStudentRequestDTO(Long classStudentId, String className, String subjectName) {
        this.classStudentId = classStudentId;
        this.className = className;
        this.subjectName = subjectName;
    }

    // Getters and Setters
    public Long getClassStudentId() {
        return classStudentId;
    }

    public void setClassStudentId(Long classStudentId) {
        this.classStudentId = classStudentId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }
}
