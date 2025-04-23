package io.spring.uni_portal.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "teaching_schedule_request")
public class TeachingScheduleRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_id")
    private Long scheduleId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "classroom_id", nullable = false)
    private Classroom classroom;

    @Column(name = "lesson", nullable = false)
    private Long lesson;

    @Column(name = "date_time", nullable = false)
    private LocalDateTime dateTime;

    @Column(name = "status", nullable = false)
    private Long status;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "class_type", nullable = false)
    private Long classType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assignment_id", nullable = false)
    private TeachingAssignment assignment;

    // Getters & Setters

    public Long getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Long scheduleId) {
        this.scheduleId = scheduleId;
    }

    public Classroom getClassroom() {
        return classroom;
    }

    public void setClassroom(Classroom classroom) {
        this.classroom = classroom;
    }

    public Long getLesson() {
        return lesson;
    }

    public void setLesson(Long lesson) {
        this.lesson = lesson;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Long getClassType() {
        return classType;
    }

    public void setClassType(Long classType) {
        this.classType = classType;
    }

    public TeachingAssignment getAssignment() {
        return assignment;
    }

    public void setAssignment(TeachingAssignment assignment) {
        this.assignment = assignment;
    }
}
