package io.spring.uni_portal.dto.StudentGrade;

public class BatchGradeResponse {
    private Long studentGradeId;
    private Double score;
    private String feedback;
    private Boolean isPassed;

    public BatchGradeResponse() {
    }

    public BatchGradeResponse(Long studentGradeId, Double score, String feedback, Boolean isPassed) {
        this.studentGradeId = studentGradeId;
        this.score = score;
        this.feedback = feedback;
        this.isPassed = isPassed;
    }

    public Long getStudentGradeId() {
        return studentGradeId;
    }

    public void setStudentGradeId(Long studentGradeId) {
        this.studentGradeId = studentGradeId;
    }

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

    public Boolean getIsPassed() {
        return isPassed;
    }

    public void setIsPassed(Boolean isPassed) {
        this.isPassed = isPassed;
    }
}

