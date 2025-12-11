package io.spring.uni_portal.dto.StudentGrade;

import java.util.List;

public class BatchGradeRequest {
    private List<StudentGradeEntry> grades;

    public List<StudentGradeEntry> getGrades() {
        return grades;
    }

    public void setGrades(List<StudentGradeEntry> grades) {
        this.grades = grades;
    }

    public static class StudentGradeEntry {
        private Long studentGradeId;
        private Double score;
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

        public String getFeedback() {
            return feedback;
        }

        public void setFeedback(String feedback) {
            this.feedback = feedback;
        }
    }
}
