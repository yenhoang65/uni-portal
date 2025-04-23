package io.spring.uni_portal.dto.Class;

public class ClassRequestDTO {
    private Long classId;
    private Long lecturerId;
    private Long trainingProgramId;
    private Long schoolYear;

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

    public Long getTrainingProgramId() {
        return trainingProgramId;
    }

    public void setTrainingProgramId(Long trainingProgramId) {
        this.trainingProgramId = trainingProgramId;
    }

    public Long getSchoolYear() {
        return schoolYear;
    }

    public void setSchoolYear(Long schoolYear) {
        this.schoolYear = schoolYear;
    }
}
