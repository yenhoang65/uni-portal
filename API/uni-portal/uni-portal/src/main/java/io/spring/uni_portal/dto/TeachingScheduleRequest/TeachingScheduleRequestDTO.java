package io.spring.uni_portal.dto.TeachingScheduleRequest;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import java.util.List;

public class TeachingScheduleRequestDTO {

    @JsonProperty("assignmentId")
    private Long assignmentId;

    @JsonProperty("status")
    private String status;

//    @JsonProperty("startDate")
//    private LocalDateTime startDate;

    @JsonProperty("materials")
    private List<MaterialDTO> materials;

    @JsonProperty("scheduleDetails")
    private List<ScheduleDetailDTO> scheduleDetails;

    public static class ScheduleDetailDTO {
        @JsonProperty("classroom_id")
        private Long classroomId;

        @JsonProperty("lesson")
        private String lesson;

        @JsonProperty("date_time")
        private String dateTime;

        @JsonProperty("end_date")
        private String endDate;

        @JsonProperty("class_type")
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

    public static class MaterialDTO {
        @JsonProperty("lt")
        private String lt;

        @JsonProperty("th")
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

    // Getters and Setters
    public Long getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(Long assignmentId) {
        this.assignmentId = assignmentId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

//    public LocalDateTime getStartDate() {
//        return startDate;
//    }
//
//    public void setStartDate(LocalDateTime startDate) {
//        this.startDate = startDate;
//    }

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
}