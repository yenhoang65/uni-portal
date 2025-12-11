package io.spring.uni_portal.dto.AttendanceSession;

import java.time.LocalDate;

public class DailyClassScheduleDTO {
    private Long sessionId;
    private String className;
    private String subjectName;
    private String classType;
    private String lecturerName;
    private String classroomName;
    private String lesson;
    private LocalDate scheduledDate;
    private String status;

    // Constructor
    public DailyClassScheduleDTO(Long sessionId, String className, String subjectName,
                                 String classType, String lecturerName, String classroomName,
                                 String lesson, LocalDate scheduledDate, String status) {
        this.sessionId = sessionId;
        this.className = className;
        this.subjectName = subjectName;
        this.classType = classType;
        this.lecturerName = lecturerName;
        this.classroomName = classroomName;
        this.lesson = lesson;
        this.scheduledDate = scheduledDate;
        this.status = status;
    }

    public Long getSessionId() {
        return sessionId;
    }

    public void setSessionId(Long sessionId) {
        this.sessionId = sessionId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getClassType() {
        return classType;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }

    public String getLecturerName() {
        return lecturerName;
    }

    public void setLecturerName(String lecturerName) {
        this.lecturerName = lecturerName;
    }

    public String getClassroomName() {
        return classroomName;
    }

    public void setClassroomName(String classroomName) {
        this.classroomName = classroomName;
    }

    public String getLesson() {
        return lesson;
    }

    public void setLesson(String lesson) {
        this.lesson = lesson;
    }

    public LocalDate getScheduledDate() {
        return scheduledDate;
    }

    public void setScheduledDate(LocalDate scheduledDate) {
        this.scheduledDate = scheduledDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

