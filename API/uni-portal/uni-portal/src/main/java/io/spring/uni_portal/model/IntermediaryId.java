package io.spring.uni_portal.model;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class IntermediaryId implements Serializable {

    @Column(name = "training_program_id")
    private Long trainingProgramId;

    @Column(name = "subject_id")
    private Long subjectId;

    public IntermediaryId() {}

    public IntermediaryId(Long trainingProgramId, Long subjectId) {
        this.trainingProgramId = trainingProgramId;
        this.subjectId = subjectId;
    }

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

    // Bắt buộc phải có equals() và hashCode()
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IntermediaryId)) return false;
        IntermediaryId that = (IntermediaryId) o;
        return Objects.equals(trainingProgramId, that.trainingProgramId) &&
                Objects.equals(subjectId, that.subjectId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(trainingProgramId, subjectId);
    }
}
