package io.spring.uni_portal.dto.Intermediary;

import java.util.List;

public class IntermediaryResponse {
    private Long trainingProgramId;
    private String trainingProgramName;
    private String schoolYear;
    private List<SubjectResponse> subjects;

    // Constructor
    public IntermediaryResponse(Long trainingProgramId, String trainingProgramName, String schoolYear, List<SubjectResponse> subjects) {
        this.trainingProgramId = trainingProgramId;
        this.trainingProgramName = trainingProgramName;
        this.schoolYear = schoolYear;
        this.subjects = subjects;
    }

    // Getters and Setters
    public Long getTrainingProgramId() {
        return trainingProgramId;
    }

    public void setTrainingProgramId(Long trainingProgramId) {
        this.trainingProgramId = trainingProgramId;
    }

    public String getTrainingProgramName() {
        return trainingProgramName;
    }

    public void setTrainingProgramName(String trainingProgramName) {
        this.trainingProgramName = trainingProgramName;
    }

    public String getSchoolYear() {
        return schoolYear;
    }

    public void setSchoolYear(String schoolYear) {
        this.schoolYear = schoolYear;
    }

    public List<SubjectResponse> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<SubjectResponse> subjects) {
        this.subjects = subjects;
    }

    // Inner Class for SubjectResponse
    public static class SubjectResponse {
        private Long subjectId;
        private String subjectName;
        private String subjectType;
        private List<PrerequisiteSubject> prerequisiteFor; // Thay đổi từ List<Long> thành List<PrerequisiteSubject>

        // Constructor
        public SubjectResponse(Long subjectId, String subjectName, String subjectType, List<PrerequisiteSubject> prerequisiteFor) {
            this.subjectId = subjectId;
            this.subjectName = subjectName;
            this.subjectType = subjectType;
            this.prerequisiteFor = prerequisiteFor;
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

        public String getSubjectType() {
            return subjectType;
        }

        public void setSubjectType(String subjectType) {
            this.subjectType = subjectType;
        }

        public List<PrerequisiteSubject> getPrerequisiteFor() {
            return prerequisiteFor;
        }

        public void setPrerequisiteFor(List<PrerequisiteSubject> prerequisiteFor) {
            this.prerequisiteFor = prerequisiteFor;
        }
    }

    // Inner Class for PrerequisiteSubject
    public static class PrerequisiteSubject {
        private Long subjectId;
        private String subjectName;

        // Constructor
        public PrerequisiteSubject(Long subjectId, String subjectName) {
            this.subjectId = subjectId;
            this.subjectName = subjectName;
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
    }
}