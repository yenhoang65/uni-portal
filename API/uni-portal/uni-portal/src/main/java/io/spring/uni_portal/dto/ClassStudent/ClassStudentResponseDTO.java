package io.spring.uni_portal.dto.ClassStudent;

import io.spring.uni_portal.dto.TeachingScheduleRequest.TeachingScheduleDetailResponseDTO;
import io.spring.uni_portal.model.*;

import java.util.List;
import java.util.Optional;
import java.util.Collections;
import java.util.stream.Collectors;

public class ClassStudentResponseDTO {

    private Long classStudentId;
    private String status;
    private String material;
    private String createdAt;
    private String endDate;
    private TeachingScheduleDetailResponseDTO schedule;

    public ClassStudentResponseDTO(ClassStudent classStudent) {
        this.classStudentId = classStudent.getClassStudentId();
        this.status = classStudent.getStatus();
        this.material = classStudent.getMaterials();
        this.createdAt = classStudent.getCreatedAt() != null ? classStudent.getCreatedAt().toString() : null;
        this.endDate = classStudent.getEndDate() != null ? classStudent.getEndDate().toString() : null;
        this.schedule = new TeachingScheduleDetailResponseDTO(classStudent.getTeachingScheduleRequest());
    }

    public Long getClassStudentId() { return classStudentId; }
    public void setClassStudentId(Long classStudentId) { this.classStudentId = classStudentId; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getMaterial() { return material; }
    public void setMaterial(String material) { this.material = material; }

    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }

    public String getEndDate() { return endDate; }
    public void setEndDate(String endDate) { this.endDate = endDate; }

    public TeachingScheduleDetailResponseDTO getSchedule() { return schedule; }
    public void setSchedule(TeachingScheduleDetailResponseDTO schedule) { this.schedule = schedule; }
}

