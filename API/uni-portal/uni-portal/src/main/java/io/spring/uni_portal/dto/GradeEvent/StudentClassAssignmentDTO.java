package io.spring.uni_portal.dto.GradeEvent;

import java.util.List;

public class StudentClassAssignmentDTO {
    private Long classStudentId;
    private String subjectName;
    private String lecturerName;
    private String classCode;
    private String scheduleTime;
    private List<StudentAssignmentDTO> assignments;

    public StudentClassAssignmentDTO() {
    }

    public StudentClassAssignmentDTO(Long classStudentId, String subjectName, String lecturerName, String classCode, String scheduleTime, List<StudentAssignmentDTO> assignments) {
        this.classStudentId = classStudentId;
        this.subjectName = subjectName;
        this.lecturerName = lecturerName;
        this.classCode = classCode;
        this.scheduleTime = scheduleTime;
        this.assignments = assignments;
    }

    public Long getClassStudentId() {
        return classStudentId;
    }

    public void setClassStudentId(Long classStudentId) {
        this.classStudentId = classStudentId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getLecturerName() {
        return lecturerName;
    }

    public void setLecturerName(String lecturerName) {
        this.lecturerName = lecturerName;
    }

    public String getClassCode() {
        return classCode;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }

    public String getScheduleTime() {
        return scheduleTime;
    }

    public void setScheduleTime(String scheduleTime) {
        this.scheduleTime = scheduleTime;
    }

    public List<StudentAssignmentDTO> getAssignments() {
        return assignments;
    }

    public void setAssignments(List<StudentAssignmentDTO> assignments) {
        this.assignments = assignments;
    }
}
