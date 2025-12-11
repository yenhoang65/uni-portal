package io.spring.uni_portal.dto.ClassSubjectStudent;

import io.spring.uni_portal.dto.Subject.SubjectDTO;

public class ClassSubjectStudentRequestDTO {
    private Long classStudentId;
    private String classname;
    private SubjectDTO subject; // ✅ Thay vì chỉ subjectName

    // Constructors
    public ClassSubjectStudentRequestDTO() {
    }

    public ClassSubjectStudentRequestDTO(Long classStudentId, String classname, SubjectDTO subject) {
        this.classStudentId = classStudentId;
        this.classname = classname;
        this.subject = subject;
    }

    // Getters and Setters
    public Long getClassStudentId() {
        return classStudentId;
    }

    public void setClassStudentId(Long classStudentId) {
        this.classStudentId = classStudentId;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public SubjectDTO getSubject() {
        return subject;
    }

    public void setSubject(SubjectDTO subject) {
        this.subject = subject;
    }
}
