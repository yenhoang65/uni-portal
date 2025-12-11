package io.spring.uni_portal.dto.TrainingProgram;

public class TrainingProgramSimpleResponse {
    private Long trainingProgramId;
    private String trainingProgramName;
    private String trainingCode;

    public TrainingProgramSimpleResponse(Long id, String name, String code) {
        this.trainingProgramId = id;
        this.trainingProgramName = name;
        this.trainingCode = code;
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

    public String getTrainingCode() {
        return trainingCode;
    }

    public void setTrainingCode(String trainingCode) {
        this.trainingCode = trainingCode;
    }
}
