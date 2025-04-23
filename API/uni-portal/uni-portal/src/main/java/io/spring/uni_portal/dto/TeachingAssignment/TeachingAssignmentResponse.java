package io.spring.uni_portal.dto.TeachingAssignment;

import io.spring.uni_portal.model.TeachingAssignment;

public class TeachingAssignmentResponse {
    private Long assignmentId;
    private Long userId;
    private String lecturerName;
    private Long subjectId;
    private String subjectName;
    private Long schoolYear;
    private Long semester;

    public TeachingAssignmentResponse(TeachingAssignment a) {
        this.assignmentId = a.getAssignmentId();
        this.userId = a.getLecturer().getUserId();
        this.lecturerName = a.getLecturer().getUser().getUserName(); // assuming user has fullName
        this.subjectId = a.getSubject().getSubjectId();
        this.subjectName = a.getSubject().getSubjectName();
        this.schoolYear = a.getSchoolYear();
        this.semester = a.getSemester();
    }

    public Long getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(Long assignmentId) {
        this.assignmentId = assignmentId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getLecturerName() {
        return lecturerName;
    }

    public void setLecturerName(String lecturerName) {
        this.lecturerName = lecturerName;
    }

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public Long getSchoolYear() {
        return schoolYear;
    }

    public void setSchoolYear(Long schoolYear) {
        this.schoolYear = schoolYear;
    }

    public Long getSemester() {
        return semester;
    }

    public void setSemester(Long semester) {
        this.semester = semester;
    }

}
