package io.spring.uni_portal.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "teaching_assignment")
public class TeachingAssignment {

    @EmbeddedId
    private TeachingAssignmentId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId") // Đảm bảo dùng userId
    @JoinColumn(name = "user_id", nullable = false) // Đảm bảo tên cột là user_id
    private Lecturer lecturer;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("subjectId") // Map field subjectId trong TeachingAssignmentId
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject;

    @Column(name = "semester", nullable = false)
    private Long semester;

    @Column(name = "school_year", nullable = false)
    private Long schoolYear;

    @Column(name = "assigned_by")
    private Long assignedBy;

    @Column(name = "assigned_at")
    private LocalDateTime assignedAt;

    public TeachingAssignment() {}

    public TeachingAssignment(Lecturer lecturer, Subject subject, Long semester, Long schoolYear, Long assignedBy, LocalDateTime assignedAt) {
        this.lecturer = lecturer;
        this.subject = subject;
        this.semester = semester;
        this.schoolYear = schoolYear;
        this.assignedBy = assignedBy;
        this.assignedAt = assignedAt;
        this.id = new TeachingAssignmentId(lecturer.getUserId(), subject.getSubjectId()); // Dùng getUserId
    }

    public TeachingAssignmentId getId() {
        return id;
    }

    public void setId(TeachingAssignmentId id) {
        this.id = id;
    }

    public Lecturer getLecturer() {
        return lecturer;
    }

    public void setLecturer(Lecturer lecturer) {
        this.lecturer = lecturer;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Long getSemester() {
        return semester;
    }

    public void setSemester(Long semester) {
        this.semester = semester;
    }

    public Long getSchoolYear() {
        return schoolYear;
    }

    public void setSchoolYear(Long schoolYear) {
        this.schoolYear = schoolYear;
    }

    public Long getAssignedBy() {
        return assignedBy;
    }

    public void setAssignedBy(Long assignedBy) {
        this.assignedBy = assignedBy;
    }

    public LocalDateTime getAssignedAt() {
        return assignedAt;
    }

    public void setAssignedAt(LocalDateTime assignedAt) {
        this.assignedAt = assignedAt;
    }
}
