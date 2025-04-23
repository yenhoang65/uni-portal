package io.spring.uni_portal.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "specialization")
public class Specialization {

    @Id
    @Column(name = "specialization_id")
    private Long specializationId;

    @Column(name = "specialization_name", nullable = false)
    private String specializationName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "major_id", nullable = false)
    private Major major;

    @Column(name = "specialization_Date_of_establishment")
    private LocalDate specializationDateOfEstablishment;

    @Column(name = "specialization_description")
    private String specializationDescription;

    @Column(name = "specialization_status")
    private String specializationStatus;

    public Specialization() {
    }

    public Specialization(String specializationName, Major major) {
        this.specializationName = specializationName;
        this.major = major;
    }

    // Getters and Setters
    public Long getSpecializationId() {
        return specializationId;
    }

    public void setSpecializationId(Long specializationId) {
        this.specializationId = specializationId;
    }

    public String getSpecializationName() {
        return specializationName;
    }

    public void setSpecializationName(String specializationName) {
        this.specializationName = specializationName;
    }

    public Major getMajor() {
        return major;
    }

    public void setMajor(Major major) {
        this.major = major;
    }

    public LocalDate getSpecializationDateOfEstablishment() {
        return specializationDateOfEstablishment;
    }

    public void setSpecializationDateOfEstablishment(LocalDate specializationDateOfEstablishment) {
        this.specializationDateOfEstablishment = specializationDateOfEstablishment;
    }

    public String getSpecializationDescription() {
        return specializationDescription;
    }

    public void setSpecializationDescription(String specializationDescription) {
        this.specializationDescription = specializationDescription;
    }

    public String getSpecializationStatus() {
        return specializationStatus;
    }

    public void setSpecializationStatus(String specializationStatus) {
        this.specializationStatus = specializationStatus;
    }

    @Override
    public String toString() {
        return "Specialization{" +
                "specializationId=" + specializationId +
                ", specializationName='" + specializationName + '\'' +
                ", major=" + (major != null ? major.getMajorName() : null) +
                ", specializationDateOfEstablishment=" + specializationDateOfEstablishment +
                ", specializationDescription='" + specializationDescription + '\'' +
                ", specializationStatus='" + specializationStatus + '\'' +
                '}';
    }
}