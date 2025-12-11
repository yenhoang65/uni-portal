package io.spring.uni_portal.dto.TeachingScheduleRequest;

import io.spring.uni_portal.dto.Subject.SubjectDTO;

import java.io.Serializable;
import java.time.LocalDateTime;

public class TeachingScheduleStatusResponseDTO implements Serializable {

    private Long scheduleId;
    private String status;
    private String lesson;
    private LocalDateTime dateTime;

    private Long assignmentId;

    private SubjectDTO subject;
    private Long lecturerId;
    private String lecturerName;

    private Long termClassId;
    private String termClassName;

    // ✅ Constructor public (bắt buộc với JPQL)
    public TeachingScheduleStatusResponseDTO(
            Long scheduleId,
            String status,
            String lesson,
            LocalDateTime dateTime,
            Long assignmentId,
            SubjectDTO subject,
            Long lecturerId,
            String lecturerName,
            Long termClassId,
            String termClassName
    ) {
        this.scheduleId = scheduleId;
        this.status = status;
        this.lesson = lesson;
        this.dateTime = dateTime;
        this.assignmentId = assignmentId;
        this.subject = subject;
        this.lecturerId = lecturerId;
        this.lecturerName = lecturerName;
        this.termClassId = termClassId;
        this.termClassName = termClassName;
    }

    // ✅ Getters + Setters
    public Long getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Long scheduleId) {
        this.scheduleId = scheduleId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public Long getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(Long assignmentId) {
        this.assignmentId = assignmentId;
    }


    public SubjectDTO getSubject() {
        return subject;
    }

    public void setSubject(SubjectDTO subject) {
        this.subject = subject;
    }

    public Long getLecturerId() {
        return lecturerId;
    }

    public void setLecturerId(Long lecturerId) {
        this.lecturerId = lecturerId;
    }

    public String getLecturerName() {
        return lecturerName;
    }

    public void setLecturerName(String lecturerName) {
        this.lecturerName = lecturerName;
    }

    public Long getTermClassId() {
        return termClassId;
    }

    public void setTermClassId(Long termClassId) {
        this.termClassId = termClassId;
    }

    public String getTermClassName() {
        return termClassName;
    }

    public void setTermClassName(String termClassName) {
        this.termClassName = termClassName;
    }
}
