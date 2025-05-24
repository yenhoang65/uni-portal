package io.spring.uni_portal.dto.Attendance;

import java.time.LocalDateTime;

public class AttendanceViewDTO {
    private Long attendanceId;
    private LocalDateTime scheduledDate;
    private String status;
    private String note;
    private Long classStudentId;
    private Long userId;

    public Long getAttendanceId() {
        return attendanceId;
    }

    public void setAttendanceId(Long attendanceId) {
        this.attendanceId = attendanceId;
    }

    public LocalDateTime getScheduledDate() {
        return scheduledDate;
    }

    public void setScheduledDate(LocalDateTime scheduledDate) {
        this.scheduledDate = scheduledDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Long getClassStudentId() {
        return classStudentId;
    }

    public void setClassStudentId(Long classStudentId) {
        this.classStudentId = classStudentId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
