package io.spring.uni_portal.dto.Transcript;

import java.util.List;

public class TranscriptResponseDTO {
    private int totalCredits;
    private int accumulatedCredits;
    private float gpa10;
    private float gpa4;
    private String classification10;
    private String classification4;
    private List<TranscriptSubjectDTO> subjects;

    // Getters and Setters
    public int getTotalCredits() {
        return totalCredits;
    }

    public void setTotalCredits(int totalCredits) {
        this.totalCredits = totalCredits;
    }

    public int getAccumulatedCredits() {
        return accumulatedCredits;
    }

    public void setAccumulatedCredits(int accumulatedCredits) {
        this.accumulatedCredits = accumulatedCredits;
    }

    public float getGpa10() {
        return gpa10;
    }

    public void setGpa10(float gpa10) {
        this.gpa10 = gpa10;
    }

    public float getGpa4() {
        return gpa4;
    }

    public void setGpa4(float gpa4) {
        this.gpa4 = gpa4;
    }

    public String getClassification10() {
        return classification10;
    }

    public void setClassification10(String classification10) {
        this.classification10 = classification10;
    }

    public String getClassification4() {
        return classification4;
    }

    public void setClassification4(String classification4) {
        this.classification4 = classification4;
    }

    public List<TranscriptSubjectDTO> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<TranscriptSubjectDTO> subjects) {
        this.subjects = subjects;
    }
}
