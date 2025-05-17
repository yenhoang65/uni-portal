package io.spring.uni_portal.dto.TeachingAssignment;

import io.spring.uni_portal.model.TeachingAssignment;

public class TeachingAssignmentDTO {

    private Long lecturerId;
    private Long subjectId;
    private Long termClassId;  // Chỉ cần trường này
    private String assignmentType;


    // Getters and setters
    public Long getLecturerId() {
        return lecturerId;
    }

    public void setLecturerId(Long lecturerId) {
        this.lecturerId = lecturerId;
    }

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }

    public Long getTermClassId() {
        return termClassId;
    }

    public void setTermClassId(Long termClassId) {
        this.termClassId = termClassId;
    }
    public String getAssignmentType() {
        return assignmentType;
    }

    public void setAssignmentType(String assignmentType) {
        this.assignmentType = assignmentType;
    }
}
