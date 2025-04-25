package io.spring.uni_portal.dto.Intermediary;

import java.util.List;

public class IntermediaryDTO {
    private Long trainingProgramId;
    private Long subjectId;
    private String schoolYear;
    private String subjectType;
    private List<Long> prerequisiteForList;

    // Getter and Setter
    public Long getTrainingProgramId() {
        return trainingProgramId;
    }

    public void setTrainingProgramId(Long trainingProgramId) {
        this.trainingProgramId = trainingProgramId;
    }

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
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

    public List<Long> getPrerequisiteForList() {
        return prerequisiteForList;
    }

    public void setPrerequisiteForList(List<Long> prerequisiteForList) {
        this.prerequisiteForList = prerequisiteForList;
    }
}

