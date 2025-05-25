package io.spring.uni_portal.dto.GradeEvent;

import java.time.LocalDate;
import java.util.List;

public class GradeEventResponseDto {

    private Long termClassId;
    private String className;
    private String semester;
    private String schoolYear;
    private List<AssignmentDto> assignments;

    // Constructor
    public GradeEventResponseDto(Long termClassId, String className, String semester, String schoolYear, List<AssignmentDto> assignments) {
        this.termClassId = termClassId;
        this.className = className;
        this.semester = semester;
        this.schoolYear = schoolYear;
        this.assignments = assignments;
    }

    // Nested DTO for assignments
    public static class AssignmentDto {
        private Long gradeEventId;
        private String title;
        private LocalDate eventDate;
        private Double maxScore;
        private String description;

        // Constructor
        public AssignmentDto(Long gradeEventId, String title, LocalDate eventDate, Double maxScore, String description) {
            this.gradeEventId = gradeEventId;
            this.title = title;
            this.eventDate = eventDate;
            this.maxScore = maxScore;
            this.description = description;
        }

        // Getters and Setters
        public Long getGradeEventId() {
            return gradeEventId;
        }

        public void setGradeEventId(Long gradeEventId) {
            this.gradeEventId = gradeEventId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public LocalDate getEventDate() {
            return eventDate;
        }

        public void setEventDate(LocalDate eventDate) {
            this.eventDate = eventDate;
        }

        public Double getMaxScore() {
            return maxScore;
        }

        public void setMaxScore(Double maxScore) {
            this.maxScore = maxScore;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }

    // Getters and Setters
    public Long getTermClassId() {
        return termClassId;
    }

    public void setTermClassId(Long termClassId) {
        this.termClassId = termClassId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getSchoolYear() {
        return schoolYear;
    }

    public void setSchoolYear(String schoolYear) {
        this.schoolYear = schoolYear;
    }

    public List<AssignmentDto> getAssignments() {
        return assignments;
    }

    public void setAssignments(List<AssignmentDto> assignments) {
        this.assignments = assignments;
    }
}
