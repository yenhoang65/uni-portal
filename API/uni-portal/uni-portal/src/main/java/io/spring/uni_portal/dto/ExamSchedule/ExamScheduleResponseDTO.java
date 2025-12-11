package io.spring.uni_portal.dto.ExamSchedule;

import java.time.LocalDate;

public class ExamScheduleResponseDTO {
    private Long examScheduleId;
    private LocalDate startDate;
    private LocalDate endDate;
    private String startTime;
    private String endTime;
    private String examForm;
    private String classroomName;

    // --- Constructors ---
    public ExamScheduleResponseDTO() {
    }

    public ExamScheduleResponseDTO(Long examScheduleId, LocalDate startDate, LocalDate endDate, String startTime, String endTime, String examForm, String classroomName) {
        this.examScheduleId = examScheduleId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.examForm = examForm;
        this.classroomName = classroomName;
    }

    // --- Getters & Setters ---
    public Long getExamScheduleId() {
        return examScheduleId;
    }

    public void setExamScheduleId(Long examScheduleId) {
        this.examScheduleId = examScheduleId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getExamForm() {
        return examForm;
    }

    public void setExamForm(String examForm) {
        this.examForm = examForm;
    }

    public String getClassroomName() {
        return classroomName;
    }

    public void setClassroomName(String classroomName) {
        this.classroomName = classroomName;
    }
}
