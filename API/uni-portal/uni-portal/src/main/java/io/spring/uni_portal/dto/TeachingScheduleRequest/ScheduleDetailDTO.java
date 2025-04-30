package io.spring.uni_portal.dto.TeachingScheduleRequest;

import java.time.LocalDateTime;

public class ScheduleDetailDTO {
    private Long classroomId;
    private Long lesson;
    private LocalDateTime createdAt;
    private LocalDateTime dateTime;
    private String classType; // 'ltCredits' hoặc 'thCredits'

    // Getters and Setters
    public Long getClassroomId() {
        return classroomId;
    }

    public void setClassroomId(Long classroomId) {
        this.classroomId = classroomId;
    }

    public Long getLesson() {
        return lesson;
    }

    public void setLesson(Long lesson) {
        this.lesson = lesson;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getClassType() {
        return classType;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }
}

