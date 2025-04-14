package io.spring.uni_portal.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "student")
public class Student {

    @Id
    @Column(name = "user_id")
    private Long userId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "education_level")
    private String educationLevel;

    @Column(name = "admission_date")
    private LocalDate admissionDate;

    @Column(name = "type_of_training")
    private String typeOfTraining;

    @ManyToOne
    @JoinColumn(name = "specialization_id", nullable = false)
    private Specialization specialization;

    @ManyToOne
    @JoinColumn(name = "class_id")
    private Class classEntity;

    // Constructors
    public Student() {}

    public Student(User user, String educationLevel, LocalDate admissionDate, String typeOfTraining,
                   Specialization specialization, Class classEntity) {
        this.user = user;
        this.educationLevel = educationLevel;
        this.admissionDate = admissionDate;
        this.typeOfTraining = typeOfTraining;
        this.specialization = specialization;
        this.classEntity = classEntity;
    }

    // Getters and Setters
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getEducationLevel() {
        return educationLevel;
    }

    public void setEducationLevel(String educationLevel) {
        this.educationLevel = educationLevel;
    }

    public LocalDate getAdmissionDate() {
        return admissionDate;
    }

    public void setAdmissionDate(LocalDate admissionDate) {
        this.admissionDate = admissionDate;
    }

    public String getTypeOfTraining() {
        return typeOfTraining;
    }

    public void setTypeOfTraining(String typeOfTraining) {
        this.typeOfTraining = typeOfTraining;
    }

    public Specialization getSpecialization() {
        return specialization;
    }

    public void setSpecialization(Specialization specialization) {
        this.specialization = specialization;
    }

    public Class getClassEntity() {
        return classEntity;
    }

    public void setClassEntity(Class classEntity) {
        this.classEntity = classEntity;
    }
}
