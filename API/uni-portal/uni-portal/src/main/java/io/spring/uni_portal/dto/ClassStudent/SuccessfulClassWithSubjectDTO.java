package io.spring.uni_portal.dto.ClassStudent;

import io.spring.uni_portal.dto.Subject.SubjectDTO;
import io.spring.uni_portal.dto.TermClass.TermClassDTO;

public class SuccessfulClassWithSubjectDTO {
    private Long classStudentId;
    private String status;
    private SubjectDTO subject;
    private TermClassDTO termClass;

    public SuccessfulClassWithSubjectDTO(Long classStudentId, String status, SubjectDTO subject, TermClassDTO termClass) {
        this.classStudentId = classStudentId;
        this.status = status;
        this.subject = subject;
        this.termClass = termClass;
    }

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

    public SubjectDTO getSubject() {
        return subject;
    }

    public void setSubject(SubjectDTO subject) {
        this.subject = subject;
    }

    public TermClassDTO getTermClass() {
        return termClass;
    }

    public void setTermClass(TermClassDTO termClass) {
        this.termClass = termClass;
    }
}
