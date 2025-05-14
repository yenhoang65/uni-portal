package io.spring.uni_portal.dto.TeachingScheduleRequest;

import java.util.List;

public class TeachingScheduleUpdateRequestDTO {
    private Long scheduleId;
    private Long assignmentId;
    private String status;
    private List<ScheduleDetailDTO> scheduleDetails;
    private List<MaterialDTO> materials;

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

    public Long getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Long scheduleId) {
        this.scheduleId = scheduleId;
    }

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