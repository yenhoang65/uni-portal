package io.spring.uni_portal.dto.TrainingProgram;

import io.spring.uni_portal.dto.Intermediary.IntermediaryDTO;
import io.spring.uni_portal.model.Subject;
public class SubjectInTrainingProgramResponse {
    private Subject subject;
    private String subjectType;
    private String schoolYear;
    private TrainingProgramDTO trainingProgram;
    private IntermediaryDTO intermediary;

    public SubjectInTrainingProgramResponse(Subject subject, String subjectType, String schoolYear, TrainingProgramDTO trainingProgram,IntermediaryDTO intermediary) {
        this.subject = subject;
        this.subjectType = subjectType;
        this.schoolYear = schoolYear;
        this.trainingProgram = trainingProgram;
        this.intermediary = intermediary;
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

    public TrainingProgramDTO getTrainingProgram() {
        return trainingProgram;
    }

    public void setTrainingProgram(TrainingProgramDTO trainingProgram) {
        this.trainingProgram = trainingProgram;
    }

    public IntermediaryDTO getIntermediary() {
        return intermediary;
    }

    public void setIntermediary(IntermediaryDTO intermediary) {
        this.intermediary = intermediary;
    }
}

