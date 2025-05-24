package io.spring.uni_portal.dto.GradeEvent;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class GradeEventResponse {
    private Long gradeEventId;
    private Long classStudentId;
    private Long gradeTypeId;
    private String title;
    private LocalDate eventDate;
    private Double maxScore;
    private String description;
    private LocalDateTime createdAt;

    // Getters and Setters
    public Long getGradeEventId() {
        return gradeEventId;
    }

    public void setGradeEventId(Long gradeEventId) {
        this.gradeEventId = gradeEventId;
    }

    public Long getClassStudentId() {
        return classStudentId;
    }

    public void setClassStudentId(Long classStudentId) {
        this.classStudentId = classStudentId;
    }

    public Long getGradeTypeId() {
        return gradeTypeId;
    }

    public void setGradeTypeId(Long gradeTypeId) {
        this.gradeTypeId = gradeTypeId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getEventDate() {
        return eventDate;
    }

    public void setEventDate(LocalDate eventDate) {
        this.eventDate = eventDate;
    }

    public Double getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(Double maxScore) {
        this.maxScore = maxScore;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
