package io.spring.uni_portal.dto.TeachingAssignment;

import io.spring.uni_portal.model.TeachingAssignment;

public class TeachingAssignmentSimpleDTO {
    private Long assignmentId;
    private String assignmentType;

    private Long lecturerId;
    private String lecturerName;

    private Long subjectId;
    private String subjectName;

    private Long termClassId;
    private String termClassName;

    public TeachingAssignmentSimpleDTO(TeachingAssignment assignment) {
        this.assignmentId = assignment.getAssignmentId();
        this.assignmentType = assignment.getAssignmentType() != null
                ? assignment.getAssignmentType().name()
                : null;

        this.lecturerId = assignment.getLecturer().getUserId();
        this.lecturerName = assignment.getLecturer().getUser().getUserName();

        this.subjectId = assignment.getSubject().getSubjectId();
        this.subjectName = assignment.getSubject().getSubjectName();

        this.termClassId = assignment.getTermClass().getTermclassId();
        this.termClassName = assignment.getTermClass().getClassname();
    }

    // Getters & Setters
    public Long getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(Long assignmentId) {
        this.assignmentId = assignmentId;
    }

    public String getAssignmentType() {
        return assignmentType;
    }

    public void setAssignmentType(String assignmentType) {
        this.assignmentType = assignmentType;
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
