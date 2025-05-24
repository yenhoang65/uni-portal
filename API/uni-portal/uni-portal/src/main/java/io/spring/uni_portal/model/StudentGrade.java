package io.spring.uni_portal.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "student_grade")
public class StudentGrade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_grade_id")
    private Long studentGradeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "class_subject_student_id", nullable = false)
    private ClassSubjectStudent classSubjectStudent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "grade_event_id", nullable = false)
    private GradeEvent gradeEvent;

    @Column(name = "score")
    private Double score;

    @Column(name = "is_passed")
    private Boolean isPassed;

    @Column(name = "feedback")
    private String feedback;

    @Column(name = "file_url")
    private String fileUrl;

        @Column(name = "submitted_at")
    private LocalDateTime submittedAt;

    // --- Constructors ---

    public StudentGrade() {}

    public StudentGrade(Long studentGradeId, ClassSubjectStudent classSubjectStudent, GradeEvent gradeEvent,
                        Double score, Boolean isPassed, String feedback,
                        String fileUrl, LocalDateTime submittedAt) {
        this.studentGradeId = studentGradeId;
        this.classSubjectStudent = classSubjectStudent;
        this.gradeEvent = gradeEvent;
        this.score = score;
        this.isPassed = isPassed;
        this.feedback = feedback;
        this.fileUrl = fileUrl;
        this.submittedAt = submittedAt;
    }

    // --- Getters and Setters ---

    public Long getStudentGradeId() {
        return studentGradeId;
    }

    public void setStudentGradeId(Long studentGradeId) {
        this.studentGradeId = studentGradeId;
    }

    public ClassSubjectStudent getClassSubjectStudent() {
        return classSubjectStudent;
    }

    public void setClassSubjectStudent(ClassSubjectStudent classSubjectStudent) {
        this.classSubjectStudent = classSubjectStudent;
    }

    public GradeEvent getGradeEvent() {
        return gradeEvent;
    }

    public void setGradeEvent(GradeEvent gradeEvent) {
        this.gradeEvent = gradeEvent;
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
}
