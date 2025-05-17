package io.spring.uni_portal.dto.TeachingAssignment;

import io.spring.uni_portal.dto.Subject.SubjectDTO;

public class TeachingAssignmentDetailDTO {
    private Long assignmentId;
    private Long lecturerId;
    private String lecturerName;
    private SubjectDTO subject;
    private Long termClassId;
    private String className;
    private String progress;
    private String semester;
    private String schoolYears;
    private String assignmentType;

    // Constructors
    public TeachingAssignmentDetailDTO(Long assignmentId, Long lecturerId, String lecturerName, SubjectDTO subject,
                                       Long termClassId, String className, String progress, String semester,
                                       String schoolYears, String assignmentType) {
        this.assignmentId = assignmentId;
        this.lecturerId = lecturerId;
        this.lecturerName = lecturerName;
        this.subject = subject;
        this.termClassId = termClassId;
        this.className = className;
        this.progress = progress;
        this.semester = semester;
        this.schoolYears = schoolYears;
        this.assignmentType = assignmentType;
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

    public SubjectDTO getSubject() {
        return subject;
    }

    public void setSubject(SubjectDTO subject) {
        this.subject = subject;
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

    public String getAssignmentType() {
        return assignmentType;
    }

    public void setAssignmentType(String assignmentType) {
        this.assignmentType = assignmentType;
    }
}
