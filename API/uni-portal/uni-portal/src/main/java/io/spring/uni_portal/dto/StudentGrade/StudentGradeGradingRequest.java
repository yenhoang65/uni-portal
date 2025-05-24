package io.spring.uni_portal.dto.StudentGrade;

public class StudentGradeGradingRequest {
    private Double score;
    private String feedback;

    // Getters and Setters
    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}
