package io.spring.uni_portal.dto.StudentGrade;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class StudentAssignmentResponse {
    private Long gradeEventId;
    private String title;
    private LocalDate eventDate;
    private Double maxScore;
    private String description;
    private LocalDateTime createdAt;
    private Long classStudentId;
    private String classStudentStatus;
    private String classStudentMaterials;
    private Long termclassId;
    private String classname;
    private String semester;
    private String schoolyears;
    private Long studentGradeId;
    private String fileUrl;
    private LocalDateTime submittedAt;
    private Double score;
    private Boolean isPassed;
    private String feedback;
    private SubmissionStatus submissionStatus;

    public enum SubmissionStatus {
        ON_TIME, LATE, NOT_SUBMITTED
    }

    // Getters and Setters
    public Long getGradeEventId() {
        return gradeEventId;
    }

    public void setGradeEventId(Long gradeEventId) {
        this.gradeEventId = gradeEventId;
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

    public Long getClassStudentId() {
        return classStudentId;
    }

    public void setClassStudentId(Long classStudentId) {
        this.classStudentId = classStudentId;
    }

    public String getClassStudentStatus() {
        return classStudentStatus;
    }

    public void setClassStudentStatus(String classStudentStatus) {
        this.classStudentStatus = classStudentStatus;
    }

    public String getClassStudentMaterials() {
        return classStudentMaterials;
    }

    public void setClassStudentMaterials(String classStudentMaterials) {
        this.classStudentMaterials = classStudentMaterials;
    }

    public Long getTermclassId() {
        return termclassId;
    }

    public void setTermclassId(Long termclassId) {
        this.termclassId = termclassId;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getSchoolyears() {
        return schoolyears;
    }

    public void setSchoolyears(String schoolyears) {
        this.schoolyears = schoolyears;
    }

    public Long getStudentGradeId() {
        return studentGradeId;
    }

    public void setStudentGradeId(Long studentGradeId) {
        this.studentGradeId = studentGradeId;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public LocalDateTime getSubmittedAt() {
        return submittedAt;
    }

    public void setSubmittedAt(LocalDateTime submittedAt) {
        this.submittedAt = submittedAt;
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

    public SubmissionStatus getSubmissionStatus() {
        return submissionStatus;
    }

    public void setSubmissionStatus(SubmissionStatus submissionStatus) {
        this.submissionStatus = submissionStatus;
    }
}
