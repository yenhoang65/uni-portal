package io.spring.uni_portal.dto.Intermediary;

public class IntermediaryResponse {
    private Long trainingProgramId;
    private String trainingProgramName;
    private Long subjectId;
    private String subjectName;
    private String schoolYear;

    public IntermediaryResponse() {}

    public IntermediaryResponse(Long trainingProgramId, String trainingProgramName,
                                Long subjectId, String subjectName, String schoolYear) {
        this.trainingProgramId = trainingProgramId;
        this.trainingProgramName = trainingProgramName;
        this.subjectId = subjectId;
        this.subjectName = subjectName;
        this.schoolYear = schoolYear;
    }

    public Long getTrainingProgramId() {
        return trainingProgramId;
    }

    public void setTrainingProgramId(Long trainingProgramId) {
        this.trainingProgramId = trainingProgramId;
    }

    public String getTrainingProgramName() {
        return trainingProgramName;
    }

    public void setTrainingProgramName(String trainingProgramName) {
        this.trainingProgramName = trainingProgramName;
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

    public String getSchoolYear() {
        return schoolYear;
    }

    public void setSchoolYear(String schoolYear) {
        this.schoolYear = schoolYear;
    }

}
