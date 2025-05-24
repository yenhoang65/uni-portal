package io.spring.uni_portal.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "grade_event")
public class GradeEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "grade_event_id")
    private Long gradeEventId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "class_student_id", nullable = false)
    private ClassStudent classStudent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "grade_type_id", nullable = false)
    private GradeType gradeType;

    @Column(name = "title")
    private String title;

    @Column(name = "event_date")
    private LocalDate eventDate;

    @Column(name = "max_score")
    private Double maxScore;


    @Column(name = "description")
    private String description;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // Getters and Setters
    // Getters and Setters

    public Long getGradeEventId() {
        return gradeEventId;
    }

    public void setGradeEventId(Long gradeEventId) {
        this.gradeEventId = gradeEventId;
    }

    public ClassStudent getClassStudent() {
        return classStudent;
    }

    public void setClassStudent(ClassStudent classStudent) {
        this.classStudent = classStudent;
    }

    public GradeType getGradeType() {
        return gradeType;
    }

    public void setGradeType(GradeType gradeType) {
        this.gradeType = gradeType;
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
