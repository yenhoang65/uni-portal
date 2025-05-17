package io.spring.uni_portal.dto.ClassSubjectStudent;

import io.spring.uni_portal.model.TeachingScheduleRequest;

import java.time.LocalDateTime;
import java.util.List;

public class RegisteredClassDTO {
    private Long classSubjectStudentId;
    private String classSubjectStudentStatus;

    private Long classStudentId;
    private String classStudentStatus;
    private LocalDateTime classStudentCreatedAt;
    private LocalDateTime classStudentEndDate;
    private String classStudentMaterials;

    private Long scheduleId;
    private LocalDateTime endDate;
    private LocalDateTime dateTime;
    private String scheduleStatus;
    private List<TeachingScheduleRequest.ScheduleDetail> scheduleDetails;
    private List<TeachingScheduleRequest.Material> materials;

    private Long assignmentId;
    private String assignmentType;

    private Long subjectId;
    private String subjectName;
    private String subjectDescription;
    private Integer ltCredits;
    private Integer thCredits;
    private Double subjectCoefficient;

    private Long termclassId;
    private String classname;
    private String schoolyears;
    private String semester;
    private String progress;

    private Long classroomId;
    private String classroomName;
    private Integer numberOfSeats;
    private String device;
    private String classroomStatus;

    // Constructors
    public RegisteredClassDTO() {}

    public RegisteredClassDTO(
            Long classSubjectStudentId, String classSubjectStudentStatus,
            Long classStudentId, String classStudentStatus, LocalDateTime classStudentCreatedAt,
            LocalDateTime classStudentEndDate, String classStudentMaterials,
            Long scheduleId, String classType, String lesson, LocalDateTime startDate,
            LocalDateTime endDate, LocalDateTime dateTime, String scheduleStatus,
            List<TeachingScheduleRequest.ScheduleDetail> scheduleDetails,
            List<TeachingScheduleRequest.Material> materials,
            Long assignmentId, String assignmentType,
            Long subjectId, String subjectName, String subjectDescription,
            Integer ltCredits, Integer thCredits, Double subjectCoefficient,
            Long termclassId, String classname, String schoolyears, String semester, String progress,
            Long classroomId, String classroomName, Integer numberOfSeats, String device, String classroomStatus
    ) {
        this.classSubjectStudentId = classSubjectStudentId;
        this.classSubjectStudentStatus = classSubjectStudentStatus;
        this.classStudentId = classStudentId;
        this.classStudentStatus = classStudentStatus;
        this.classStudentCreatedAt = classStudentCreatedAt;
        this.classStudentEndDate = classStudentEndDate;
        this.classStudentMaterials = classStudentMaterials;
        this.scheduleId = scheduleId;
        this.endDate = endDate;
        this.dateTime = dateTime;
        this.scheduleStatus = scheduleStatus;
        this.scheduleDetails = scheduleDetails;
        this.materials = materials;
        this.assignmentId = assignmentId;
        this.assignmentType = assignmentType;
        this.subjectId = subjectId;
        this.subjectName = subjectName;
        this.subjectDescription = subjectDescription;
        this.ltCredits = ltCredits;
        this.thCredits = thCredits;
        this.subjectCoefficient = subjectCoefficient;
        this.termclassId = termclassId;
        this.classname = classname;
        this.schoolyears = schoolyears;
        this.semester = semester;
        this.progress = progress;
        this.classroomId = classroomId;
        this.classroomName = classroomName;
        this.numberOfSeats = numberOfSeats;
        this.device = device;
        this.classroomStatus = classroomStatus;
    }

    // Getters and Setters
    // Auto-generated
    public Long getClassSubjectStudentId() { return classSubjectStudentId; }
    public void setClassSubjectStudentId(Long id) { this.classSubjectStudentId = id; }

    public String getClassSubjectStudentStatus() { return classSubjectStudentStatus; }
    public void setClassSubjectStudentStatus(String status) { this.classSubjectStudentStatus = status; }

    public Long getClassStudentId() { return classStudentId; }
    public void setClassStudentId(Long id) { this.classStudentId = id; }

    public String getClassStudentStatus() { return classStudentStatus; }
    public void setClassStudentStatus(String status) { this.classStudentStatus = status; }

    public LocalDateTime getClassStudentCreatedAt() { return classStudentCreatedAt; }
    public void setClassStudentCreatedAt(LocalDateTime date) { this.classStudentCreatedAt = date; }

    public LocalDateTime getClassStudentEndDate() { return classStudentEndDate; }
    public void setClassStudentEndDate(LocalDateTime date) { this.classStudentEndDate = date; }

    public String getClassStudentMaterials() { return classStudentMaterials; }
    public void setClassStudentMaterials(String m) { this.classStudentMaterials = m; }

    public Long getScheduleId() { return scheduleId; }
    public void setScheduleId(Long id) { this.scheduleId = id; }


    public LocalDateTime getEndDate() { return endDate; }
    public void setEndDate(LocalDateTime date) { this.endDate = date; }

    public LocalDateTime getDateTime() { return dateTime; }
    public void setDateTime(LocalDateTime dateTime) { this.dateTime = dateTime; }

    public String getScheduleStatus() { return scheduleStatus; }
    public void setScheduleStatus(String status) { this.scheduleStatus = status; }

    public List<TeachingScheduleRequest.ScheduleDetail> getScheduleDetails() {
        return scheduleDetails;
    }

    public void setScheduleDetails(List<TeachingScheduleRequest.ScheduleDetail> scheduleDetails) {
        this.scheduleDetails = scheduleDetails;
    }

    public List<TeachingScheduleRequest.Material> getMaterials() {
        return materials;
    }

    public void setMaterials(List<TeachingScheduleRequest.Material> materials) {
        this.materials = materials;
    }


    public Long getAssignmentId() { return assignmentId; }
    public void setAssignmentId(Long id) { this.assignmentId = id; }

    public String getAssignmentType() { return assignmentType; }
    public void setAssignmentType(String type) { this.assignmentType = type; }

    public Long getSubjectId() { return subjectId; }
    public void setSubjectId(Long id) { this.subjectId = id; }

    public String getSubjectName() { return subjectName; }
    public void setSubjectName(String name) { this.subjectName = name; }

    public String getSubjectDescription() { return subjectDescription; }
    public void setSubjectDescription(String desc) { this.subjectDescription = desc; }

    public Integer getLtCredits() { return ltCredits; }
    public void setLtCredits(Integer credits) { this.ltCredits = credits; }

    public Integer getThCredits() { return thCredits; }
    public void setThCredits(Integer credits) { this.thCredits = credits; }

    public Double getSubjectCoefficient() { return subjectCoefficient; }
    public void setSubjectCoefficient(Double coef) { this.subjectCoefficient = coef; }

    public Long getTermclassId() { return termclassId; }
    public void setTermclassId(Long id) { this.termclassId = id; }

    public String getClassname() { return classname; }
    public void setClassname(String name) { this.classname = name; }

    public String getSchoolyears() { return schoolyears; }
    public void setSchoolyears(String years) { this.schoolyears = years; }

    public String getSemester() { return semester; }
    public void setSemester(String sem) { this.semester = sem; }

    public String getProgress() { return progress; }
    public void setProgress(String progress) { this.progress = progress; }

    public Long getClassroomId() { return classroomId; }
    public void setClassroomId(Long id) { this.classroomId = id; }

    public String getClassroomName() { return classroomName; }
    public void setClassroomName(String name) { this.classroomName = name; }

    public Integer getNumberOfSeats() { return numberOfSeats; }
    public void setNumberOfSeats(Integer num) { this.numberOfSeats = num; }

    public String getDevice() { return device; }
    public void setDevice(String device) { this.device = device; }

    public String getClassroomStatus() { return classroomStatus; }
    public void setClassroomStatus(String status) { this.classroomStatus = status; }
}