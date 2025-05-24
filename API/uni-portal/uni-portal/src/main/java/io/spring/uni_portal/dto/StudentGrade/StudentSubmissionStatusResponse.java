package io.spring.uni_portal.dto.StudentGrade;

import io.spring.uni_portal.model.TeachingAssignment;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class StudentSubmissionStatusResponse {
    // Student fields
    private Long studentId;
    private String studentName; // Giả định Student có trường name
    private String studentCode; // Giả định Student có trường code

    // ClassSubjectStudent fields
    private Long classSubjectStudentId;
    private String classSubjectStudentStatus;
    private LocalDateTime registrationTime;

    // StudentGrade fields
    private Long studentGradeId;
    private Double score;
    private Boolean isPassed;
    private String feedback;
    private String fileUrl;
    private LocalDateTime submittedAt;
    private SubmissionStatus submissionStatus;

    // GradeEvent fields
    private Long gradeEventId;
    private String gradeEventTitle;
    private LocalDate eventDate;
    private Double maxScore;
    private String gradeEventDescription;
    private LocalDateTime gradeEventCreatedAt;

    // ClassStudent fields
    private Long classStudentId;
    private String classStudentStatus;
    private LocalDateTime classStudentCreatedAt;
    private LocalDateTime classStudentEndDate;
    private String classStudentMaterials;

    // TeachingAssignment fields
    private Long assignmentId;
    private TeachingAssignment.AssignmentType assignmentType;

    public enum SubmissionStatus {
        ON_TIME, LATE, NOT_SUBMITTED
    }

    // Getters and Setters
    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentCode() {
        return studentCode;
    }

    public void setStudentCode(String studentCode) {
        this.studentCode = studentCode;
    }

    public Long getClassSubjectStudentId() {
        return classSubjectStudentId;
    }

    public void setClassSubjectStudentId(Long classSubjectStudentId) {
        this.classSubjectStudentId = classSubjectStudentId;
    }

    public String getClassSubjectStudentStatus() {
        return classSubjectStudentStatus;
    }

    public void setClassSubjectStudentStatus(String classSubjectStudentStatus) {
        this.classSubjectStudentStatus = classSubjectStudentStatus;
    }

    public LocalDateTime getRegistrationTime() {
        return registrationTime;
    }

    public void setRegistrationTime(LocalDateTime registrationTime) {
        this.registrationTime = registrationTime;
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

    public SubmissionStatus getSubmissionStatus() {
        return submissionStatus;
    }

    public void setSubmissionStatus(SubmissionStatus submissionStatus) {
        this.submissionStatus = submissionStatus;
    }

    public Long getGradeEventId() {
        return gradeEventId;
    }

    public void setGradeEventId(Long gradeEventId) {
        this.gradeEventId = gradeEventId;
    }

    public String getGradeEventTitle() {
        return gradeEventTitle;
    }

    public void setGradeEventTitle(String gradeEventTitle) {
        this.gradeEventTitle = gradeEventTitle;
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

    public String getGradeEventDescription() {
        return gradeEventDescription;
    }

    public void setGradeEventDescription(String gradeEventDescription) {
        this.gradeEventDescription = gradeEventDescription;
    }

    public LocalDateTime getGradeEventCreatedAt() {
        return gradeEventCreatedAt;
    }

    public void setGradeEventCreatedAt(LocalDateTime gradeEventCreatedAt) {
        this.gradeEventCreatedAt = gradeEventCreatedAt;
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

    public LocalDateTime getClassStudentCreatedAt() {
        return classStudentCreatedAt;
    }

    public void setClassStudentCreatedAt(LocalDateTime classStudentCreatedAt) {
        this.classStudentCreatedAt = classStudentCreatedAt;
    }

    public LocalDateTime getClassStudentEndDate() {
        return classStudentEndDate;
    }

    public void setClassStudentEndDate(LocalDateTime classStudentEndDate) {
        this.classStudentEndDate = classStudentEndDate;
    }

    public String getClassStudentMaterials() {
        return classStudentMaterials;
    }

    public void setClassStudentMaterials(String classStudentMaterials) {
        this.classStudentMaterials = classStudentMaterials;
    }

    public Long getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(Long assignmentId) {
        this.assignmentId = assignmentId;
    }

    public TeachingAssignment.AssignmentType getAssignmentType() {
        return assignmentType;
    }

    public void setAssignmentType(TeachingAssignment.AssignmentType assignmentType) {
        this.assignmentType = assignmentType;
    }
}
