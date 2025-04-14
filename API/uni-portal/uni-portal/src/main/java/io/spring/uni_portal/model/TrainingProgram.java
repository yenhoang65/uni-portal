package io.spring.uni_portal.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "training_program")
public class TrainingProgram {

    @Id
    @Column(name = "training_program_id")
    private Long trainingProgramId;

    @Column(name = "training_code", nullable = false)
    private String trainingCode;

    @Column(name = "training_program_name", nullable = false)
    private String trainingProgramName;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "specialization_id", nullable = false)
    private Specialization specialization;

    // Constructors
    public TrainingProgram() {}

    public TrainingProgram(Long trainingProgramId, String trainingCode, String trainingProgramName,
                           LocalDateTime createdAt, Specialization specialization) {
        this.trainingProgramId = trainingProgramId;
        this.trainingCode = trainingCode;
        this.trainingProgramName = trainingProgramName;
        this.createdAt = createdAt;
        this.specialization = specialization;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Specialization getSpecialization() {
        return specialization;
    }

    public void setSpecialization(Specialization specialization) {
        this.specialization = specialization;
    }
}
