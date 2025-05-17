package io.spring.uni_portal.dto.TeachingAssignment;

import io.spring.uni_portal.model.TeachingScheduleRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class TeachingScheduleSimpleDTO {
    private Long scheduleId;
    private Long classroomId;
    private String lesson;
    private LocalDateTime dateTime;
    private LocalDateTime endDate;
    private String status;
    private String classType;
    private LocalDateTime createdAt;

    private List<ScheduleDetailDTO> scheduleDetails;
    private List<MaterialDTO> materials;

    // Constructor from Entity
    public TeachingScheduleSimpleDTO(TeachingScheduleRequest entity) {
        this.scheduleId = entity.getScheduleId();
        this.classroomId = entity.getClassroom() != null ? entity.getClassroom().getClassroomId() : null;
        this.lesson = entity.getLesson();
        this.dateTime = entity.getDateTime();
        this.endDate = entity.getEndDate();
        this.status = entity.getStatus();
        this.classType = entity.getClassType();
        this.createdAt = entity.getCreatedAt();

        if (entity.getScheduleDetails() != null) {
            this.scheduleDetails = entity.getScheduleDetails().stream().map(d -> {
                ScheduleDetailDTO dto = new ScheduleDetailDTO();
                dto.setClassroomId(d.getClassroomId());
                dto.setLesson(d.getLesson());
                dto.setDateTime(d.getDateTime());
                dto.setEndDate(d.getEndDate());
                dto.setClassType(d.getClassType());
                return dto;
            }).collect(Collectors.toList());
        }

        if (entity.getMaterials() != null) {
            this.materials = entity.getMaterials().stream().map(m -> {
                MaterialDTO dto = new MaterialDTO();
                dto.setLt(m.getLt());
                dto.setTh(m.getTh());
                return dto;
            }).collect(Collectors.toList());
        }
    }

    // Nested DTO: Schedule Detail
    public static class ScheduleDetailDTO {
        private Long classroomId;
        private String lesson;
        private String dateTime;
        private String endDate;
        private String classType;

        public Long getClassroomId() {
            return classroomId;
        }

        public void setClassroomId(Long classroomId) {
            this.classroomId = classroomId;
        }

        public String getLesson() {
            return lesson;
        }

        public void setLesson(String lesson) {
            this.lesson = lesson;
        }

        public String getDateTime() {
            return dateTime;
        }

        public void setDateTime(String dateTime) {
            this.dateTime = dateTime;
        }

        public String getEndDate() {
            return endDate;
        }

        public void setEndDate(String endDate) {
            this.endDate = endDate;
        }

        public String getClassType() {
            return classType;
        }

        public void setClassType(String classType) {
            this.classType = classType;
        }
    }

    // Nested DTO: Material
    public static class MaterialDTO {
        private String lt;
        private String th;

        public String getLt() {
            return lt;
        }

        public void setLt(String lt) {
            this.lt = lt;
        }

        public String getTh() {
            return th;
        }

        public void setTh(String th) {
            this.th = th;
        }
    }

    // Getters & Setters
    public Long getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Long scheduleId) {
        this.scheduleId = scheduleId;
    }

    public Long getClassroomId() {
        return classroomId;
    }

    public void setClassroomId(Long classroomId) {
        this.classroomId = classroomId;
    }

    public String getLesson() {
        return lesson;
    }

    public void setLesson(String lesson) {
        this.lesson = lesson;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getClassType() {
        return classType;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<ScheduleDetailDTO> getScheduleDetails() {
        return scheduleDetails;
    }

    public void setScheduleDetails(List<ScheduleDetailDTO> scheduleDetails) {
        this.scheduleDetails = scheduleDetails;
    }

    public List<MaterialDTO> getMaterials() {
        return materials;
    }

    public void setMaterials(List<MaterialDTO> materials) {
        this.materials = materials;
    }
}
