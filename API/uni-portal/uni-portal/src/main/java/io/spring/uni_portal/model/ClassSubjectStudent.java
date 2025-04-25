package io.spring.uni_portal.model;

import jakarta.persistence.*;

@Entity
@Table(name = "class_subject_student")
public class ClassSubjectStudent {

    @Id
    @Column(name = "termclass_id")
    private Long termclassId;

    @ManyToOne
    @MapsId
    @JoinColumn(name = "termclass_id")
    private TermClass termClass;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private Student student;

    @Column(name = "status")
    private Long status;

    // Constructors
    public ClassSubjectStudent() {}

    public ClassSubjectStudent(TermClass termClass, Student student, Long status) {
        this.termClass = termClass;
        this.student = student;
        this.status = status;
    }

    // Getters and Setters
    public Long getTermclassId() {
        return termclassId;
    }

    public void setTermclassId(Long termclassId) {
        this.termclassId = termclassId;
    }

    public TermClass getTermClass() {
        return termClass;
    }

    public void setTermClass(TermClass termClass) {
        this.termClass = termClass;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }
}

