package io.spring.uni_portal.dto.TeachingAssignment;

public class TeachingAssignmentResponseDTO {

    private Long assignmentId;
    private Long lecturerId;
    private String lecturerName;
    private Long subjectId;
    private String subjectName;
    private Long termClassId;
    private String className;
    private String progress;
    private String semester;
    private String schoolYears;

    // Getters, Setters, Constructors
    public TeachingAssignmentResponseDTO(Long assignmentId, Long lecturerId, String lecturerName, Long subjectId,
                                         String subjectName, Long termClassId, String className, String progress,
                                         String semester, String schoolYears) {
        this.assignmentId = assignmentId;
        this.lecturerId = lecturerId;
        this.lecturerName = lecturerName;
        this.subjectId = subjectId;
        this.subjectName = subjectName;
        this.termClassId = termClassId;
        this.className = className;
        this.progress = progress;
        this.semester = semester;
        this.schoolYears = schoolYears;
    }

    // Getters and Setters

    public Long getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(Long assignmentId) {
        this.assignmentId = assignmentId;
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

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getSchoolYears() {
        return schoolYears;
    }

    public void setSchoolYears(String schoolYears) {
        this.schoolYears = schoolYears;
    }
}