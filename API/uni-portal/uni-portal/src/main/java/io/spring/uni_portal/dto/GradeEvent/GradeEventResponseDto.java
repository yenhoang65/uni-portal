package io.spring.uni_portal.dto.GradeEvent;

import io.spring.uni_portal.dto.ExamSchedule.ExamScheduleResponse;

import java.time.LocalDate;
import java.util.List;

public class GradeEventResponseDto {

    private Long termClassId;
    private String className;
    private String semester;
    private String schoolYear;
    private List<AssignmentDto> assignments;

    public GradeEventResponseDto() {}

    public GradeEventResponseDto(Long termClassId, String className, String semester, String schoolYear, List<AssignmentDto> assignments) {
        this.termClassId = termClassId;
        this.className = className;
        this.semester = semester;
        this.schoolYear = schoolYear;
        this.assignments = assignments;
    }

    // ========== Nested DTO for Assignment ==========
    public static class AssignmentDto {
        private Long gradeEventId;
        private String title;
        private LocalDate eventDate;
        private Double maxScore;
        private String description;
        private GradeTypeEventDTO gradeType;
        private ExamScheduleResponse examSchedule;


        public AssignmentDto() {}

        public AssignmentDto(Long gradeEventId, String title, LocalDate eventDate, Double maxScore, String description, GradeTypeEventDTO gradeType, ExamScheduleResponse examSchedule) {
            this.gradeEventId = gradeEventId;
            this.title = title;
            this.eventDate = eventDate;
            this.maxScore = maxScore;
            this.description = description;
            this.gradeType = gradeType;
            this.examSchedule = examSchedule;
        }

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

        public GradeTypeEventDTO getGradeType() {
            return gradeType;
        }

        public void setGradeType(GradeTypeEventDTO gradeType) {
            this.gradeType = gradeType;
        }
        public ExamScheduleResponse getExamSchedule() {
            return examSchedule;
        }

        public void setExamSchedule(ExamScheduleResponse examSchedule) {
            this.examSchedule = examSchedule;
        }
    }

    // ========== Nested DTO for Grade Type ==========
    public static class GradeTypeEventDTO {
        private Long id;
        private String code;
        private String name;
        private Double coefficient;

        public GradeTypeEventDTO() {}

        public GradeTypeEventDTO(Long id, String code, String name, Double coefficient) {
            this.id = id;
            this.code = code;
            this.name = name;
            this.coefficient = coefficient;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Double getCoefficient() {
            return coefficient;
        }

        public void setCoefficient(Double coefficient) {
            this.coefficient = coefficient;
        }
    }

    // ========== Getters & Setters ==========
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
