package io.spring.uni_portal.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "term_class")
public class TermClass {

    @Id
    @Column(name = "term_class_id")
    private Long termClassId;

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

    @Column(name = "date_time")
    private LocalDateTime dateTime;

    @ManyToOne
    @JoinColumn(name = "classroom_id")
    private Classroom classroom;

    @Column(name = "status")
    private Long status;

    @Column(name = "approved_by")
    private Long approvedBy;

    @Column(name = "minimum_quantity")
    private Long minimumQuantity;

    @Column(name = "semester")
    private Long semester;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    // Constructors
    public TermClass() {
    }

    public TermClass(String className, Long classType, Subject subject, String lesson, Lecturer lecturer, LocalDateTime dateTime, Classroom classroom, Long status, Long approvedBy, Long minimumQuantity, Long semester, LocalDateTime createdAt, LocalDateTime endDate) {
        this.className = className;
        this.classType = classType;
        this.subject = subject;
        this.lesson = lesson;
        this.lecturer = lecturer;
        this.dateTime = dateTime;
        this.classroom = classroom;
        this.status = status;
        this.approvedBy = approvedBy;
        this.minimumQuantity = minimumQuantity;
        this.semester = semester;
        this.createdAt = createdAt;
        this.endDate = endDate;
    }

    // Getters and Setters
    public Long getTermClassId() {
        return termClassId;
    }

    public void setTermClassId(Long termClassId) {
        this.termClassId = termClassId;
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

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Classroom getClassroom() {
        return classroom;
    }

    public void setClassroom(Classroom classroom) {
        this.classroom = classroom;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public Long getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(Long approvedBy) {
        this.approvedBy = approvedBy;
    }

    public Long getMinimumQuantity() {
        return minimumQuantity;
    }

    public void setMinimumQuantity(Long minimumQuantity) {
        this.minimumQuantity = minimumQuantity;
    }

    public Long getSemester() {
        return semester;
    }

    public void setSemester(Long semester) {
        this.semester = semester;
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
