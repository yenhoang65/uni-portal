package io.spring.uni_portal.dto.ExamSchedule;

import io.spring.uni_portal.dto.Classroom.ClassroomDTO;
import io.spring.uni_portal.model.ExamSchedule;

import java.time.LocalDate;

public class ExamScheduleAdminResponse {
    private Long examScheduleId;
    private String subjectName;
    private String className;
    private String examForm;
    private LocalDate startDate;
    private LocalDate endDate;
    private String startTime;
    private String endTime;
    private ClassroomDTO classroom;

    public ExamScheduleAdminResponse(ExamSchedule schedule) {
        this.examScheduleId = schedule.getExamScheduleId();
        this.subjectName = schedule.getClassStudent()
                .getTeachingScheduleRequest().getAssignment().getSubject().getSubjectName();
        this.className = schedule.getClassStudent()
                .getTeachingScheduleRequest().getAssignment().getTermClass().getClassname();
        this.examForm = schedule.getExamForm();
        this.startDate = schedule.getStartDate();
        this.endDate = schedule.getEndDate();
        this.startTime = schedule.getStartTime();
        this.endTime = schedule.getEndTime();

        if (schedule.getClassroom() != null) {
            this.classroom = new ClassroomDTO();
            this.classroom.setClassroomId(schedule.getClassroom().getClassroomId());
            this.classroom.setClassroomName(schedule.getClassroom().getClassroomName());
            this.classroom.setNumberOfSeats(schedule.getClassroom().getNumberOfSeats());
            this.classroom.setStatus(schedule.getClassroom().getStatus());
        }
    }

    // Getters & setters
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

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getExamForm() {
        return examForm;
    }

    public void setExamForm(String examForm) {
        this.examForm = examForm;
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

    public ClassroomDTO getClassroom() {
        return classroom;
    }

    public void setClassroom(ClassroomDTO classroom) {
        this.classroom = classroom;
    }
}
