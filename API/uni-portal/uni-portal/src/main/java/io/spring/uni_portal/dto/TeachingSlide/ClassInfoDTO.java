package io.spring.uni_portal.dto.TeachingSlide;

public class ClassInfoDTO {
    private Long classStudentId;
    private String subjectName;
    private String lecturerName;
    private String schoolYear;
    private String semester;
    private String className;

    public ClassInfoDTO() {}

    public ClassInfoDTO(Long classStudentId, String subjectName, String lecturerName,
                        String schoolYear, String semester, String className) {
        this.classStudentId = classStudentId;
        this.subjectName = subjectName;
        this.lecturerName = lecturerName;
        this.schoolYear = schoolYear;
        this.semester = semester;
        this.className = className;
    }

    public Long getClassStudentId() {
        return classStudentId;
    }

    public void setClassStudentId(Long classStudentId) {
        this.classStudentId = classStudentId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getLecturerName() {
        return lecturerName;
    }

    public void setLecturerName(String lecturerName) {
        this.lecturerName = lecturerName;
    }

    public String getSchoolYear() {
        return schoolYear;
    }

    public void setSchoolYear(String schoolYear) {
        this.schoolYear = schoolYear;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}