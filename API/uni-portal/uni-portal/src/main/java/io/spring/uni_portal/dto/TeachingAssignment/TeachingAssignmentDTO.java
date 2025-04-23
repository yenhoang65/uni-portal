package io.spring.uni_portal.dto.TeachingAssignment;

public class TeachingAssignmentDTO {
    private Long userId;
    private Long subjectId;
    private Long schoolYear;
    private Long semester;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
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
