package io.spring.uni_portal.dto.ExamSchedule;

import io.spring.uni_portal.model.ExamSchedule;

import java.time.LocalDate;

public class ExamScheduleStudentResponse {
    private Long examScheduleId;
    private String subjectName;
    private String examForm;
    private String gradeType;
    private LocalDate startDate;
    private LocalDate endDate;
    private String startTime;
    private String endTime;
    private String classroomName;
    private Long termclassId;
    private String className;

    public ExamScheduleStudentResponse(ExamSchedule es) {
        this.examScheduleId = es.getExamScheduleId();
        this.subjectName = es.getClassStudent().getTeachingScheduleRequest().getAssignment().getSubject().getSubjectName();
        this.examForm = es.getExamForm();
        this.gradeType = es.getGradeType().getCode();
        this.startDate = es.getStartDate();
        this.endDate = es.getEndDate();
        this.startTime = es.getStartTime();
        this.endTime = es.getEndTime();
        this.classroomName = es.getClassroom().getClassroomName();
        this.termclassId = es.getClassStudent().getTeachingScheduleRequest().getAssignment().getTermClass().getTermclassId();
        this.className = es.getClassStudent().getTeachingScheduleRequest().getAssignment().getTermClass().getClassname();
    }

    public Long getExamScheduleId() {
        return examScheduleId;
    }

    public void setExamScheduleId(Long examScheduleId) {
        this.examScheduleId = examScheduleId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getExamForm() {
        return examForm;
    }

    public void setExamForm(String examForm) {
        this.examForm = examForm;
    }

    public String getGradeType() {
        return gradeType;
    }

    public void setGradeType(String gradeType) {
        this.gradeType = gradeType;
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

    public String getClassroomName() {
        return classroomName;
    }

    public void setClassroomName(String classroomName) {
        this.classroomName = classroomName;
    }

    public Long getTermclassId() {
        return termclassId;
    }

    public void setTermclassId(Long termclassId) {
        this.termclassId = termclassId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
