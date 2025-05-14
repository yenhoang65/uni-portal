package io.spring.uni_portal.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "teaching_schedule_request")
public class TeachingScheduleRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_id")
    private Long scheduleId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "classroom_id", nullable = true)
    private Classroom classroom;

    @Column(name = "lesson", nullable = true)
    private String lesson;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @Column(name = "date_time", nullable = true)
    private LocalDateTime dateTime;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "created_at", nullable = true, updatable = true)
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assignment_id", nullable = true)
    private TeachingAssignment assignment;

    @Column(name = "class_type", nullable = true)
    private String classType;

    @Column(name = "materials", columnDefinition = "jsonb")
    @JdbcTypeCode(SqlTypes.JSON)
    private List<Material> materials;

    @Column(name = "schedule_details", columnDefinition = "jsonb")
    @JdbcTypeCode(SqlTypes.JSON)
    private List<ScheduleDetail> scheduleDetails;

    public static class Material {
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

    public static class ScheduleDetail {
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

    // Getters & Setters
    public Long getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Long scheduleId) {
        this.scheduleId = scheduleId;
    }

    public Classroom getClassroom() {
        return classroom;
    }

    public void setClassroom(Classroom classroom) {
        this.classroom = classroom;
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

    public String getClassType() {
        return classType;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }

    public TeachingAssignment getAssignment() {
        return assignment;
    }

    public void setAssignment(TeachingAssignment assignment) {
        this.assignment = assignment;
    }

    public List<ScheduleDetail> getScheduleDetails() {
        return scheduleDetails;
    }

    public void setScheduleDetails(List<ScheduleDetail> scheduleDetails) {
        this.scheduleDetails = scheduleDetails;
    }

    public List<Material> getMaterials() {
        return materials;
    }

    public void setMaterials(List<Material> materials) {
        this.materials = materials;
    }
}