package io.spring.uni_portal.dto.TeachingAssignment;

public class TeachingAssignmentDTO {

    private Long lecturerId;
    private Long subjectId;
    private Long termClassId;  // Chỉ cần trường này

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
}
