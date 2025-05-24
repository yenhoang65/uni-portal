package io.spring.uni_portal.model;


import jakarta.persistence.*;

@Entity
@Table(name = "attendance")
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attendance_id")
    private Long attendanceId;

    @ManyToOne
    @JoinColumn(name = "session_id")
    private AttendanceSession attendanceSession;

    @ManyToOne
    @JoinColumn(name = "class_subject_student_id")
    private ClassSubjectStudent classSubjectStudent;

    @Column(name = "status")
    private String status;

    @Column(name = "note")
    private String note;

    // Getters and setters
    public Long getAttendanceId() {
        return attendanceId;
    }

    public void setAttendanceId(Long attendanceId) {
        this.attendanceId = attendanceId;
    }

    public AttendanceSession getAttendanceSession() {
        return attendanceSession;
    }

    public void setAttendanceSession(AttendanceSession attendanceSession) {
        this.attendanceSession = attendanceSession;
    }

    public ClassSubjectStudent getClassSubjectStudent() {
        return classSubjectStudent;
    }

    public void setClassSubjectStudent(ClassSubjectStudent classSubjectStudent) {
        this.classSubjectStudent = classSubjectStudent;
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
}
