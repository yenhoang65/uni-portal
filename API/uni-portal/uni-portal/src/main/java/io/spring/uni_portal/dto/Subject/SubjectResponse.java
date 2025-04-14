package io.spring.uni_portal.dto.Subject;

public class SubjectResponse {
    private Long subjectId;
    private String subjectName;
    private Integer ltCredits;
    private Integer thCredits;
    private String subjectDescription;
    private String subjectType;
    private Double subjectCoefficient;

    public SubjectResponse() {}

    public SubjectResponse(Long subjectId, String subjectName, Integer ltCredits, Integer thCredits,
                           String subjectDescription, String subjectType, Double subjectCoefficient) {
        this.subjectId = subjectId;
        this.subjectName = subjectName;
        this.ltCredits = ltCredits;
        this.thCredits = thCredits;
        this.subjectDescription = subjectDescription;
        this.subjectType = subjectType;
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

    public String getSubjectType() {
        return subjectType;
    }

    public void setSubjectType(String subjectType) {
        this.subjectType = subjectType;
    }

    public Double getSubjectCoefficient() {
        return subjectCoefficient;
    }

    public void setSubjectCoefficient(Double subjectCoefficient) {
        this.subjectCoefficient = subjectCoefficient;
    }
}
