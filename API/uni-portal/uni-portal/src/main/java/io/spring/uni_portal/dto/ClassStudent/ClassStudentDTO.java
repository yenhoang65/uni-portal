package io.spring.uni_portal.dto.ClassStudent;

import io.spring.uni_portal.model.ClassStudent;
import io.spring.uni_portal.model.TeachingScheduleRequest;
import java.time.LocalDateTime;

public class ClassStudentDTO {

    private Long classStudentId;
    private String status;
    private Long scheduleId;
    private LocalDateTime createdAt;
    private LocalDateTime endDate;
    private String materials;
    private Long studentId;
    private Long classId;

    // Constructor
    public ClassStudentDTO() {}

    public ClassStudentDTO(Long classStudentId, String status, Long scheduleId, LocalDateTime createdAt,
                           LocalDateTime endDate, String materials, Long studentId, Long classId) {
        this.classStudentId = classStudentId;
        this.status = status;
        this.scheduleId = scheduleId;
        this.createdAt = createdAt;
        this.endDate = endDate;
        this.materials = materials;
        this.studentId = studentId;
        this.classId = classId;
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

    public Long getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Long scheduleId) {
        this.scheduleId = scheduleId;
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

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Long getClassId() {
        return classId;
    }

    public void setClassId(Long classId) {
        this.classId = classId;
    }
}
