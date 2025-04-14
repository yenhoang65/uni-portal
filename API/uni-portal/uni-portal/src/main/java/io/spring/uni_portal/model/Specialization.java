package io.spring.uni_portal.model;

import jakarta.persistence.*;

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

    // Constructors
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

    // Optional: toString()
    @Override
    public String toString() {
        return "Specialization{" +
                "specializationId=" + specializationId +
                ", specializationName='" + specializationName + '\'' +
                ", major=" + (major != null ? major.getMajorName() : null) +
                '}';
    }
}