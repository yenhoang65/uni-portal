package io.spring.uni_portal.dto.TrainingProgram;

import java.time.LocalDateTime;

import java.time.LocalDateTime;

public class TrainingProgramResponse {
    private Long trainingProgramId;
    private String trainingCode;
    private String trainingProgramName;
    private Long specializationId;
    private String specializationName;
    private LocalDateTime createdAt;

    public TrainingProgramResponse() {}

    public TrainingProgramResponse(Long trainingProgramId, String trainingCode, String trainingProgramName
                                   ,Long specializationId,String specializationName, LocalDateTime createdAt) {
        this.trainingProgramId = trainingProgramId;
        this.trainingCode = trainingCode;
        this.trainingProgramName = trainingProgramName;
        this.specializationId = specializationId;
        this.specializationName = specializationName;
        this.createdAt = createdAt;
    }

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

    public Long getSpecializationId() {
        return specializationId;
    }

    public void setSpecializationId(Long specializationId) {
        this.specializationId = specializationId;
    }

    public void setTrainingProgramName(String trainingProgramName) {
        this.trainingProgramName = trainingProgramName;
    }

    public String getSpecializationName() {
        return specializationName;
    }

    public void setSpecializationName(String specializationName) {
        this.specializationName = specializationName;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
