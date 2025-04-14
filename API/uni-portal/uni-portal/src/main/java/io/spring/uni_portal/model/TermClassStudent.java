package io.spring.uni_portal.model;

import jakarta.persistence.*;

@Entity
@Table(name = "term_class_student")
public class TermClassStudent {

    @Id
    @ManyToOne
    @JoinColumn(name = "term_class_id", referencedColumnName = "term_class_id")
    private TermClass termClass;

    @Id
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private Student student;

    @Column(name = "status")
    private Long status;

    // Constructors
    public TermClassStudent() {}

    public TermClassStudent(TermClass termClass, Student student, Long status) {
        this.termClass = termClass;
        this.student = student;
        this.status = status;
    }

    // Getters and Setters
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
