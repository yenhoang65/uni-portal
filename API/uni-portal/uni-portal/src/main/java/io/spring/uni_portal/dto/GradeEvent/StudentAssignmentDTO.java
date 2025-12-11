package io.spring.uni_portal.dto.GradeEvent;
import java.time.LocalDate;

public class StudentAssignmentDTO {
    private Long assignmentId;
    private String title;
    private LocalDate eventDate;
    private double maxScore;
    private double coefficient;
    private String description;
    private String gradeTypeName;

    public StudentAssignmentDTO() {}

    public StudentAssignmentDTO(Long assignmentId, String title, LocalDate eventDate, double maxScore,
                                double coefficient, String description, String gradeTypeName) {
        this.assignmentId = assignmentId;
        this.title = title;
        this.eventDate = eventDate;
        this.maxScore = maxScore;
        this.coefficient = coefficient;
        this.description = description;
        this.gradeTypeName = gradeTypeName;
    }

    // Getters and Setters
    public Long getAssignmentId() { return assignmentId; }
    public void setAssignmentId(Long assignmentId) { this.assignmentId = assignmentId; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public LocalDate getEventDate() { return eventDate; }
    public void setEventDate(LocalDate eventDate) { this.eventDate = eventDate; }
    public double getMaxScore() { return maxScore; }
    public void setMaxScore(double maxScore) { this.maxScore = maxScore; }
    public double getCoefficient() { return coefficient; }
    public void setCoefficient(double coefficient) { this.coefficient = coefficient; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getGradeTypeName() { return gradeTypeName; }
    public void setGradeTypeName(String gradeTypeName) { this.gradeTypeName = gradeTypeName; }
}