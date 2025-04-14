package io.spring.uni_portal.model;

import jakarta.persistence.*;

@Entity
@Table(name = "class")
public class Class {

    @Id
    @Column(name = "class_id")
    private Long classId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id") // Tham chiếu đến user_id
    private Lecturer lecturer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "training_program_id", nullable = false)
    private TrainingProgram trainingProgram;

    @Column(name = "school_year")
    private Long schoolYear;

    // Constructors
    public Class() {
    }

    public Class(Lecturer lecturer, TrainingProgram trainingProgram, Long schoolYear) {
        this.lecturer = lecturer;
        this.trainingProgram = trainingProgram;
        this.schoolYear = schoolYear;
    }

    // Getters and Setters
    public Long getClassId() {
        return classId;
    }

    public void setClassId(Long classId) {
        this.classId = classId;
    }

    public Lecturer getLecturer() {
        return lecturer;
    }

    public void setLecturer(Lecturer lecturer) {
        this.lecturer = lecturer;
    }

    public TrainingProgram getTrainingProgram() {
        return trainingProgram;
    }

    public void setTrainingProgram(TrainingProgram trainingProgram) {
        this.trainingProgram = trainingProgram;
    }

    public Long getSchoolYear() {
        return schoolYear;
    }

    public void setSchoolYear(Long schoolYear) {
        this.schoolYear = schoolYear;
    }

    // toString
    @Override
    public String toString() {
        return "Class{" +
                "classId=" + classId +
                ", lecturer=" + (lecturer != null ? lecturer.getUserId() : null) +
                ", trainingProgram=" + (trainingProgram != null ? trainingProgram.getTrainingCode() : null) +
                ", schoolYear=" + schoolYear +
                '}';
    }
}
