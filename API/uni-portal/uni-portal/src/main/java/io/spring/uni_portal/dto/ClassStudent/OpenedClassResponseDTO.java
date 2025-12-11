package io.spring.uni_portal.dto.ClassStudent;

import io.spring.uni_portal.dto.Attendance.SuccessfulClassDTO;
import io.spring.uni_portal.dto.Subject.SubjectDTO;
import io.spring.uni_portal.dto.TermClass.TermClassDTO;
import io.spring.uni_portal.dto.TeachingScheduleRequest.TeachingScheduleRequestDTO;

import java.time.LocalDateTime;

public class OpenedClassResponseDTO {
    private Long classStudentId;
    private String status;
    private LocalDateTime createdAt;

    private SubjectDTO subject;
    private TermClassDTO termClass;
    private SuccessfulClassDTO.TeachingAssignmentDTO.LecturerDTO lecturer;
    private TeachingScheduleRequestDTO scheduleRequest;

    public OpenedClassResponseDTO() {}

    public OpenedClassResponseDTO(Long classStudentId, String status, LocalDateTime createdAt,
                                  SubjectDTO subject, TermClassDTO termClass,
                                  SuccessfulClassDTO.TeachingAssignmentDTO.LecturerDTO lecturer,
                                  TeachingScheduleRequestDTO scheduleRequest) {
        this.classStudentId = classStudentId;
        this.status = status;
        this.createdAt = createdAt;
        this.subject = subject;
        this.termClass = termClass;
        this.lecturer = lecturer;
        this.scheduleRequest = scheduleRequest;
    }

    public Long getClassStudentId() {
        return classStudentId;
    }

    public void setClassStudentId(Long classStudentId) {
        this.classStudentId = classStudentId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public SubjectDTO getSubject() {
        return subject;
    }

    public void setSubject(SubjectDTO subject) {
        this.subject = subject;
    }

    public TermClassDTO getTermClass() {
        return termClass;
    }

    public void setTermClass(TermClassDTO termClass) {
        this.termClass = termClass;
    }

    public SuccessfulClassDTO.TeachingAssignmentDTO.LecturerDTO getLecturer() {
        return lecturer;
    }

    public void setLecturer(SuccessfulClassDTO.TeachingAssignmentDTO.LecturerDTO lecturer) {
        this.lecturer = lecturer;
    }

    public TeachingScheduleRequestDTO getScheduleRequest() {
        return scheduleRequest;
    }

    public void setScheduleRequest(TeachingScheduleRequestDTO scheduleRequest) {
        this.scheduleRequest = scheduleRequest;
    }
}
