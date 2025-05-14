package io.spring.uni_portal.model;

import jakarta.persistence.*;

@Entity
@Table(name = "class_subject_student")
public class ClassSubjectStudent {

    @Id
    @Column(name = "class_subject_student_id")
    private Long classSubjectStudentId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "termclass_id", nullable = false)
    private ClassStudent classStudent;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private Student student;

    @Column(name = "status")
    private Long status;

    // Constructors
    public ClassSubjectStudent() {}

    public ClassSubjectStudent(ClassStudent classStudent, Student student, Long status) {
        this.classStudent = classStudent;
        this.student = student;
        this.status = status;
    }

    // Getters and Setters
    public Long getClassSubjectStudentId() {
        return classSubjectStudentId;
    }

    public void setClassSubjectStudentId(Long classSubjectStudentId) {
        this.classSubjectStudentId = classSubjectStudentId;
    }

    public ClassStudent getClassStudent() {
        return classStudent;
    }

    public void setClassStudent(ClassStudent classStudent) {
        this.classStudent = classStudent;
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
