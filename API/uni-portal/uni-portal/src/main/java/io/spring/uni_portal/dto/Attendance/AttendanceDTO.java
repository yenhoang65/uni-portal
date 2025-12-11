package io.spring.uni_portal.dto.Attendance;

import java.time.LocalDateTime;

public class AttendanceDTO {
    private Long sessionId;
    private LocalDateTime sessionDate;
    private String status;
    private String note;

    // Getters và Setters
    public Long getSessionId() { return sessionId; }
    public void setSessionId(Long sessionId) { this.sessionId = sessionId; }
    public LocalDateTime getSessionDate() { return sessionDate; }
    public void setSessionDate(LocalDateTime sessionDate) { this.sessionDate = sessionDate; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }
}
