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

    @Column(name = "subject_type", nullable = false)
    private String subjectType;

    @Column(name = "prerequisite_for")
    private String prerequisiteFor;

    public Intermediary() {}

    public Intermediary(TrainingProgram trainingProgram, Subject subject, String schoolYear, String subjectType) {
        this.trainingProgram = trainingProgram;
        this.subject = subject;
        this.schoolYear = schoolYear;
        this.subjectType = subjectType;
        this.id = new IntermediaryId(
                trainingProgram.getTrainingProgramId(),
                subject.getSubjectId()
        );
    }

    // Getter and Setter for id
    public IntermediaryId getId() {
        return id;
    }

    public void setId(IntermediaryId id) {
        this.id = id;
    }

    // Getter and Setter for trainingProgram
    public TrainingProgram getTrainingProgram() {
        return trainingProgram;
    }

    public void setTrainingProgram(TrainingProgram trainingProgram) {
        this.trainingProgram = trainingProgram;
    }

    // Getter and Setter for subject
    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    // Getter and Setter for schoolYear
    public String getSchoolYear() {
        return schoolYear;
    }

    public void setSchoolYear(String schoolYear) {
        this.schoolYear = schoolYear;
    }

    // Getter and Setter for subjectType
    public String getSubjectType() {
        return subjectType;
    }

    public void setSubjectType(String subjectType) {
        this.subjectType = subjectType;
    }

    // Getter and Setter for prerequisiteFor
    public String getPrerequisiteFor() {
        return prerequisiteFor;
    }

    public void setPrerequisiteFor(String prerequisiteFor) {
        this.prerequisiteFor = prerequisiteFor;
    }

    @Override
    public String toString() {
        return "Intermediary{" +
                "trainingProgram=" + trainingProgram.getTrainingCode() +
                ", subject=" + subject.getSubjectName() +
                ", schoolYear='" + schoolYear + '\'' +
                ", subjectType='" + subjectType + '\'' +
                ", prerequisiteFor='" + prerequisiteFor + '\'' +
                '}';
    }
}
