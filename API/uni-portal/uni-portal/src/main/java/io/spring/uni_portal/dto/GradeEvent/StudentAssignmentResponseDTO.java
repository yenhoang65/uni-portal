package io.spring.uni_portal.dto.GradeEvent;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class StudentAssignmentResponseDTO {

    private String classname;
    private String subjectName;
    private String gradeTypeName;
    private String title;
    private String description;
    private LocalDateTime eventDate;
    private Double maxScore;
    private Double coefficient;

    public StudentAssignmentResponseDTO(String classname, String subjectName, String gradeTypeName,
                                        String title, String description, LocalDateTime eventDate,
                                        Double maxScore, Double coefficient) {
        this.classname = classname;
        this.subjectName = subjectName;
        this.gradeTypeName = gradeTypeName;
        this.title = title;
        this.description = description;
        this.eventDate = eventDate;
        this.maxScore = maxScore;
        this.coefficient = coefficient;
    }

    // ✅ Getters
    public String getClassname() {
        return classname;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public String getGradeTypeName() {
        return gradeTypeName;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getEventDate() {
        return eventDate;
    }

    public Double getMaxScore() {
        return maxScore;
    }

    public Double getCoefficient() {
        return coefficient;
    }
}
