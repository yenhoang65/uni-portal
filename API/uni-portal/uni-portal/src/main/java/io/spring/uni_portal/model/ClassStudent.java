package io.spring.uni_portal.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "class_student")
public class ClassStudent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "class_student_id")
    private Long classStudentId;

    @Column(name = "status")
    private String status;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id", nullable = false)
    private TeachingScheduleRequest teachingScheduleRequest;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @Column(name = "materials")
    private String materials;

    // Constructors
    public ClassStudent() {
    }

    public ClassStudent(TermClass termClass, TeachingScheduleRequest teachingScheduleRequest, String status,
                        LocalDateTime createdAt, LocalDateTime endDate, String materials) {
        this.teachingScheduleRequest = teachingScheduleRequest;
        this.status = status;
        this.createdAt = createdAt;
        this.endDate = endDate;
        this.materials = materials;
    }

    // Getters and Setters
    public Long getClassStudentId() {
        return classStudentId;
    }

    public void setClassStudentId(Long classStudentId) {
        this.classStudentId = classStudentId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public TeachingScheduleRequest getTeachingScheduleRequest() {
        return teachingScheduleRequest;
    }

    public void setTeachingScheduleRequest(TeachingScheduleRequest teachingScheduleRequest) {
        this.teachingScheduleRequest = teachingScheduleRequest;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public String getMaterials() {
        return materials;
    }

    public void setMaterials(String materials) {
        this.materials = materials;
    }
}
