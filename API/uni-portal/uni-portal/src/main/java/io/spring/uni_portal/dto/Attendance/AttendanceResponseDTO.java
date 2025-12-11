package io.spring.uni_portal.dto.Attendance;

public class AttendanceResponseDTO {
    private Long attendanceId;
    private Long userId;
    private String studentName;
    private String status;

    // Constructor mới có attendanceId
    public AttendanceResponseDTO(Long attendanceId, Long userId, String studentName, String status) {
        this.attendanceId = attendanceId;
        this.userId = userId;
        this.studentName = studentName;
        this.status = status;
    }

    // Getters & Setters
    public Long getAttendanceId() { return attendanceId; }
    public void setAttendanceId(Long attendanceId) { this.attendanceId = attendanceId; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
