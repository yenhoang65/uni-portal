package io.spring.uni_portal.dto.Transcript;

import java.util.Map;

public class TranscriptSubjectDTO {
    private Long subjectId;
    private String subjectName;
    private Integer ltCredits;
    private Integer thCredits;
    private String subjectDescription;
    private Double subjectCoefficient;
    private Map<String, Float> componentScores;
    private Float finalScore;
    private Float averageScore;
    private Float gpa4;
    private String letter;

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

    public Map<String, Float> getComponentScores() {
        return componentScores;
    }

    public void setComponentScores(Map<String, Float> componentScores) {
        this.componentScores = componentScores;
    }

    public Float getFinalScore() {
        return finalScore;
    }

    public void setFinalScore(Float finalScore) {
        this.finalScore = finalScore;
    }

    public Float getAverageScore() {
        return averageScore;
    }

    public void setAverageScore(Float averageScore) {
        this.averageScore = averageScore;
    }

    public Float getGpa4() {
        return gpa4;
    }

    public void setGpa4(Float gpa4) {
        this.gpa4 = gpa4;
    }

    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }
}
