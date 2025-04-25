package io.spring.uni_portal.model;

import jakarta.persistence.*;

@Entity
@Table(name = "subject")
public class Subject {

    @Id
    @Column(name = "subject_id")
    private Long subjectId;

    @Column(name = "subject_name", nullable = false)
    private String subjectName;

    @Column(name = "lt_credits")
    private Integer ltCredits;

    @Column(name = "th_credits")
    private Integer thCredits;

    @Column(name = "subject_description")
    private String subjectDescription;


    @Column(name = "subject_coefficient")
    private Double subjectCoefficient;

    // Constructors
    public Subject() {}

    public Subject(String subjectName, Integer ltCredits, Integer thCredits, String subjectDescription, Double subjectCoefficient) {
        this.subjectName = subjectName;
        this.ltCredits = ltCredits;
        this.thCredits = thCredits;
        this.subjectDescription = subjectDescription;
        this.subjectCoefficient = subjectCoefficient;
    }

    // Getters and Setters
    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public Integer getLtCredits() {
        return ltCredits;
    }

    public void setLtCredits(Integer ltCredits) {
        this.ltCredits = ltCredits;
    }

    public Integer getThCredits() {
        return thCredits;
    }

    public void setThCredits(Integer thCredits) {
        this.thCredits = thCredits;
    }

    public String getSubjectDescription() {
        return subjectDescription;
    }

    public void setSubjectDescription(String subjectDescription) {
        this.subjectDescription = subjectDescription;
    }

    public Double getSubjectCoefficient() {
        return subjectCoefficient;
    }

    public void setSubjectCoefficient(Double subjectCoefficient) {
        this.subjectCoefficient = subjectCoefficient;
    }

    @Override
    public String toString() {
        return "Subject{" +
                "subjectId=" + subjectId +
                ", subjectName='" + subjectName + '\'' +
                ", ltCredits=" + ltCredits +
                ", thCredits=" + thCredits +
                ", subjectDescription='" + subjectDescription + '\'' +
                ", subjectCoefficient=" + subjectCoefficient +
                '}';
    }
}
