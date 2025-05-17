package io.spring.uni_portal.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "class_subject_student")
public class ClassSubjectStudent {

    @Id
    @Column(name = "class_subject_student_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long classSubjectStudentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "class_student_id", nullable = false)
    private ClassStudent classStudent;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private Student student;

    @Column(name = "status")
    private String status;

    private LocalDateTime registrationTime; // thời gian đăng ký 

    // Constructors
    public ClassSubjectStudent() {}

    public ClassSubjectStudent(ClassStudent classStudent, Student student, String status) {
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getRegistrationTime() {
        return registrationTime;
    }

    public void setRegistrationTime(LocalDateTime registrationTime) {
        this.registrationTime = registrationTime;
    }
}
