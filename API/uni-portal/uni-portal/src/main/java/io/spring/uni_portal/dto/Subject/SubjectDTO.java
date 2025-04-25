package io.spring.uni_portal.dto.Subject;

public class SubjectDTO {
    private Long subjectId;
    private String subjectName;
    private Integer ltCredits;
    private Integer thCredits;
    private String subjectDescription;
    private Double subjectCoefficient;

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
}
