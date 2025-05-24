package io.spring.uni_portal.dto.Attendance;

public class AttendanceRequestDTO {
    private Long sessionId;
    private Long classSubjectStudentId;
    private String status;
    private String note;

    // Getters and setters
    public Long getSessionId() { return sessionId; }
    public void setSessionId(Long sessionId) { this.sessionId = sessionId; }

    public Long getClassSubjectStudentId() { return classSubjectStudentId; }
    public void setClassSubjectStudentId(Long classSubjectStudentId) { this.classSubjectStudentId = classSubjectStudentId; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }
}
