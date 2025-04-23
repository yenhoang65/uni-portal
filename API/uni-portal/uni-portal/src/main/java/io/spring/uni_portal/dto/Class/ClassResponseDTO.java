package io.spring.uni_portal.dto.Class;

import io.spring.uni_portal.model.Class;

public class ClassResponseDTO {
    private Long classId;
    private Long lecturerId;
    private String lecturerName;
    private Long trainingProgramId;
    private String trainingProgramName;
    private Long schoolYear;

    public ClassResponseDTO(Class entity) {
        this.classId = entity.getClassId();
        this.lecturerId = entity.getLecturer().getUserId();
        this.lecturerName = entity.getLecturer().getUser().getUserName();
        this.trainingProgramId = entity.getTrainingProgram().getTrainingProgramId();
        this.trainingProgramName = entity.getTrainingProgram().getTrainingProgramName();
        this.schoolYear = entity.getSchoolYear();
    }

    public Long getClassId() {
        return classId;
    }

    public void setClassId(Long classId) {
        this.classId = classId;
    }

    public Long getLecturerId() {
        return lecturerId;
    }

    public void setLecturerId(Long lecturerId) {
        this.lecturerId = lecturerId;
    }

    public String getLecturerName() {
        return lecturerName;
    }

    public void setLecturerName(String lecturerName) {
        this.lecturerName = lecturerName;
    }

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

    public Long getSchoolYear() {
        return schoolYear;
    }

    public void setSchoolYear(Long schoolYear) {
        this.schoolYear = schoolYear;
    }
}
