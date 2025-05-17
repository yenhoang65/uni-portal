package io.spring.uni_portal.dto.Subject;

public class SubjectResponseDTO {
    private Long subjectId;
    private String subjectName;
    private String schoolYear;
    private String subjectType;
    private String prerequisiteFor;

    // Constructors
    public SubjectResponseDTO() {}

    public SubjectResponseDTO(Long subjectId, String subjectName, String schoolYear, String subjectType, String prerequisiteFor) {
        this.subjectId = subjectId;
        this.subjectName = subjectName;
        this.schoolYear = schoolYear;
        this.subjectType = subjectType;
        this.prerequisiteFor = prerequisiteFor;
    }

    // Getters and Setters
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

    public String getSchoolYear() {
        return schoolYear;
    }

    public void setSchoolYear(String schoolYear) {
        this.schoolYear = schoolYear;
    }

    public String getSubjectType() {
        return subjectType;
    }

    public void setSubjectType(String subjectType) {
        this.subjectType = subjectType;
    }

    public String getPrerequisiteFor() {
        return prerequisiteFor;
    }

    public void setPrerequisiteFor(String prerequisiteFor) {
        this.prerequisiteFor = prerequisiteFor;
    }
}

