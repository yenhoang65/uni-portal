package io.spring.uni_portal.model;
import jakarta.persistence.*;

@Entity
@Table(name = "intermediary")
public class Intermediary {

    @EmbeddedId
    private IntermediaryId id;

    @ManyToOne
    @MapsId("trainingProgramId")
    @JoinColumn(name = "training_program_id", nullable = false)
    private TrainingProgram trainingProgram;

    @ManyToOne
    @MapsId("subjectId")
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject;

    @Column(name = "school_year", nullable = false)
    private String schoolYear;

    public Intermediary() {}

    public Intermediary(TrainingProgram trainingProgram, Subject subject, String schoolYear) {
        this.trainingProgram = trainingProgram;
        this.subject = subject;
        this.schoolYear = schoolYear;
        this.id = new IntermediaryId(
                trainingProgram.getTrainingProgramId(),
                subject.getSubjectId()
        );
    }

    public IntermediaryId getId() {
        return id;
    }

    public void setId(IntermediaryId id) {
        this.id = id;
    }

    public TrainingProgram getTrainingProgram() {
        return trainingProgram;
    }

    public void setTrainingProgram(TrainingProgram trainingProgram) {
        this.trainingProgram = trainingProgram;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public String getSchoolYear() {
        return schoolYear;
    }

    public void setSchoolYear(String schoolYear) {
        this.schoolYear = schoolYear;
    }

    @Override
    public String toString() {
        return "Intermediary{" +
                "trainingProgram=" + trainingProgram.getTrainingCode() +
                ", subject=" + subject.getSubjectName() +
                ", schoolYear='" + schoolYear + '\'' +
                '}';
    }
}
