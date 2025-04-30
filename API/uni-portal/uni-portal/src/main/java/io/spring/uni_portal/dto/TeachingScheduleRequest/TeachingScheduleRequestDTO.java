package io.spring.uni_portal.dto.TeachingScheduleRequest;

import java.time.LocalDateTime;
import java.util.List;

public class TeachingScheduleRequestDTO {
    private Long scheduleId;
    private List<String> classTypes;  // List of class types
    private Long status;
    private LocalDateTime createdAt;

    // Getter for scheduleId
    public Long getScheduleId() {
        return scheduleId;
    }

    // Setter for scheduleId
    public void setScheduleId(Long scheduleId) {
        this.scheduleId = scheduleId;
    }

    // Getter for classTypes
    public List<String> getClassTypes() {
        return classTypes;
    }

    // Setter for classTypes
    public void setClassTypes(List<String> classTypes) {
        this.classTypes = classTypes;
    }

    // Getter for status
    public Long getStatus() {
        return status;
    }

    // Setter for status
    public void setStatus(Long status) {
        this.status = status;
    }

    // Getter for createdAt
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    // Setter for createdAt
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}

