package io.spring.uni_portal.dto.TrainingProgram;

public class TrainingProgramDTO {
    private Long trainingProgramId;
    private String trainingCode;
    private String trainingProgramName;
    private Long specializationId;

    // Constructors
    public TrainingProgramDTO() {}

    public TrainingProgramDTO(Long trainingProgramId, String trainingCode, String trainingProgramName, Long specializationId) {
        this.trainingProgramId = trainingProgramId;
        this.trainingCode = trainingCode;
        this.trainingProgramName = trainingProgramName;
        this.specializationId = specializationId;
    }

    // Getters and Setters
    public Long getTrainingProgramId() {
        return trainingProgramId;
    }

    public void setTrainingProgramId(Long trainingProgramId) {
        this.trainingProgramId = trainingProgramId;
    }

    public String getTrainingCode() {
        return trainingCode;
    }

    public void setTrainingCode(String trainingCode) {
        this.trainingCode = trainingCode;
    }

    public String getTrainingProgramName() {
        return trainingProgramName;
    }

    public void setTrainingProgramName(String trainingProgramName) {
        this.trainingProgramName = trainingProgramName;
    }

    public Long getSpecializationId() {
        return specializationId;
    }

    public void setSpecializationId(Long specializationId) {
        this.specializationId = specializationId;
    }
}
