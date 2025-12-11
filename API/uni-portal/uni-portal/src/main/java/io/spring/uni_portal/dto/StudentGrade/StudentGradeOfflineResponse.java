package io.spring.uni_portal.dto.StudentGrade;

public class StudentGradeOfflineResponse {
    private Long studentGradeId;
    private Double score;
    private Boolean isPassed;
    private String feedback;

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

    public Boolean getIsPassed() {
        return isPassed;
    }

    public void setIsPassed(Boolean isPassed) {
        this.isPassed = isPassed;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}
