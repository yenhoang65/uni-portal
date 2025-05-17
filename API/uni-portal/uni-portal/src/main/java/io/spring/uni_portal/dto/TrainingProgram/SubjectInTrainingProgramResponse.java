package io.spring.uni_portal.dto.TrainingProgram;

import io.spring.uni_portal.model.Subject;

public class SubjectInTrainingProgramResponse {
    private Subject subject;
    private String subjectType;
    private String schoolYear;

    public SubjectInTrainingProgramResponse(Subject subject, String subjectType, String schoolYear) {
        this.subject = subject;
        this.subjectType = subjectType;
        this.schoolYear = schoolYear;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public String getSubjectType() {
        return subjectType;
    }

    public void setSubjectType(String subjectType) {
        this.subjectType = subjectType;
    }

    public String getSchoolYear() {
        return schoolYear;
    }

    public void setSchoolYear(String schoolYear) {
        this.schoolYear = schoolYear;
    }
}
