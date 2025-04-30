package io.spring.uni_portal.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "class_student")
public class ClassStudent {

    @Id
    @Column(name = "termclass_id")
    private Long termclassId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "termclass_id")
    private TermClass termClass;

    @Column(name = "class_name")
    private String className;

    @Column(name = "class_type")
    private Long classType;

    @ManyToOne
    @JoinColumn(name = "subject_id")
    private Subject subject;

    @Column(name = "lesson")
    private String lesson;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Lecturer lecturer;

    @ManyToOne
    @JoinColumn(name = "classroom_id")
    private Classroom classroom;

    @Column(name = "status")
    private String status;

    @Column(name = "minimum_quantity")
    private Long minimumQuantity;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    // Constructors
    public ClassStudent() {
    }

    public ClassStudent(Long classType, Subject subject, String lesson, Lecturer lecturer, Classroom classroom, String status, Long minimumQuantity, LocalDateTime createdAt, LocalDateTime endDate) {
        this.classType = classType;
        this.subject = subject;
        this.lesson = lesson;
        this.lecturer = lecturer;
        this.classroom = classroom;
        this.status = status;
        this.minimumQuantity = minimumQuantity;
        this.createdAt = createdAt;
        this.endDate = endDate;
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

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Long getClassType() {
        return classType;
    }

    public void setClassType(Long classType) {
        this.classType = classType;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public String getLesson() {
        return lesson;
    }

    public void setLesson(String lesson) {
        this.lesson = lesson;
    }

    public Lecturer getLecturer() {
        return lecturer;
    }

    public void setLecturer(Lecturer lecturer) {
        this.lecturer = lecturer;
    }

    public Classroom getClassroom() {
        return classroom;
    }

    public void setClassroom(Classroom classroom) {
        this.classroom = classroom;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getMinimumQuantity() {
        return minimumQuantity;
    }

    public void setMinimumQuantity(Long minimumQuantity) {
        this.minimumQuantity = minimumQuantity;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }
}
