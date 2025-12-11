package io.spring.uni_portal.dto.TeachingScheduleRequest;

import java.time.LocalDateTime;
import java.util.List;

public class TeachingScheduleRequestClassDTO {
    private Long scheduleId;
    private Long classroomId;
    private String lesson;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private LocalDateTime dateTime;
    private String status;
    private LocalDateTime createdAt;
    private Long assignmentId;
    private String classType;
    private List<MaterialDTO> materials;
    private List<ScheduleDetailDTO> scheduleDetails;

    public TeachingScheduleRequestClassDTO() {}

    public TeachingScheduleRequestClassDTO(Long scheduleId,
                                           Long classroomId,
                                           String lesson,
                                           LocalDateTime startDate,
                                           LocalDateTime endDate,
                                           LocalDateTime dateTime,
                                           String status,
                                           LocalDateTime createdAt,
                                           Long assignmentId,
                                           String classType,
                                           List<MaterialDTO> materials,
                                           List<ScheduleDetailDTO> scheduleDetails) {
        this.scheduleId = scheduleId;
        this.classroomId = classroomId;
        this.lesson = lesson;
        this.startDate = startDate;
        this.endDate = endDate;
        this.dateTime = dateTime;
        this.status = status;
        this.createdAt = createdAt;
        this.assignmentId = assignmentId;
        this.classType = classType;
        this.materials = materials;
        this.scheduleDetails = scheduleDetails;
    }

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

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Long getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(Long assignmentId) {
        this.assignmentId = assignmentId;
    }

    public String getClassType() {
        return classType;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }

    public List<MaterialDTO> getMaterials() {
        return materials;
    }

    public void setMaterials(List<MaterialDTO> materials) {
        this.materials = materials;
    }

    public List<ScheduleDetailDTO> getScheduleDetails() {
        return scheduleDetails;
    }

    public void setScheduleDetails(List<ScheduleDetailDTO> scheduleDetails) {
        this.scheduleDetails = scheduleDetails;
    }

    public static class MaterialDTO {
        private String lt;
        private String th;

        public MaterialDTO() {}

        public MaterialDTO(String lt, String th) {
            this.lt = lt;
            this.th = th;
        }

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

    public static class ScheduleDetailDTO {
        private Long classroomId;
        private String lesson;
        private String dateTime;
        private String endDate;
        private String classType;

        public ScheduleDetailDTO() {}

        public ScheduleDetailDTO(Long classroomId, String lesson, String dateTime, String endDate, String classType) {
            this.classroomId = classroomId;
            this.lesson = lesson;
            this.dateTime = dateTime;
            this.endDate = endDate;
            this.classType = classType;
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
}
