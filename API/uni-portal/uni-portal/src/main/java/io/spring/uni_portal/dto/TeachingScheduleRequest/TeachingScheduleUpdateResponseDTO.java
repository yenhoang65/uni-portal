package io.spring.uni_portal.dto.TeachingScheduleRequest;

import io.spring.uni_portal.model.TeachingAssignment;
import io.spring.uni_portal.model.Subject;

import java.time.LocalDateTime;
import java.util.List;

public class TeachingScheduleUpdateResponseDTO {

    private Long scheduleId;
    private TeachingAssignmentDTO assignment;
    private Long classroomId;
    private LocalDateTime dateTime;
    private LocalDateTime endDate;
    private String status;
    private LocalDateTime createdAt;
    private List<ScheduleDetailDTO> scheduleDetails;
    private List<MaterialDTO> materials;

    public static class TeachingAssignmentDTO {
        private Long assignmentId;
        private LecturerDTO lecturer;
        private SubjectDTO subject;
        private TermClassDTO termClass;
        private TeachingAssignment.AssignmentType assignmentType;

        public static class LecturerDTO {
            private Long lecturerId;
            private String lecturerName;

            public Long getLecturerId() { return lecturerId; }
            public void setLecturerId(Long lecturerId) { this.lecturerId = lecturerId; }
            public String getLecturerName() { return lecturerName; }
            public void setLecturerName(String lecturerName) { this.lecturerName = lecturerName; }
        }

        public static class SubjectDTO {
            private Long subjectId;
            private String subjectName;
            private Integer ltCredits;
            private Integer thCredits;

            public Long getSubjectId() { return subjectId; }
            public void setSubjectId(Long subjectId) { this.subjectId = subjectId; }
            public String getSubjectName() { return subjectName; }
            public void setSubjectName(String subjectName) { this.subjectName = subjectName; }
            public Integer getLtCredits() { return ltCredits != null ? ltCredits : 0; }
            public void setLtCredits(Integer ltCredits) { this.ltCredits = ltCredits; }
            public Integer getThCredits() { return thCredits != null ? thCredits : 0; }
            public void setThCredits(Integer thCredits) { this.thCredits = thCredits; }
        }

        public static class TermClassDTO {
            private Long termClassId;
            private String termName;

            public Long getTermClassId() { return termClassId; }
            public void setTermClassId(Long termClassId) { this.termClassId = termClassId; }
            public String getTermName() { return termName; }
            public void setTermName(String termName) { this.termName = termName; }
        }

        public Long getAssignmentId() { return assignmentId; }
        public void setAssignmentId(Long assignmentId) { this.assignmentId = assignmentId; }
        public LecturerDTO getLecturer() { return lecturer; }
        public void setLecturer(LecturerDTO lecturer) { this.lecturer = lecturer; }
        public SubjectDTO getSubject() { return subject; }
        public void setSubject(SubjectDTO subject) { this.subject = subject; }
        public TermClassDTO getTermClass() { return termClass; }
        public void setTermClass(TermClassDTO termClass) { this.termClass = termClass; }
        public TeachingAssignment.AssignmentType getAssignmentType() { return assignmentType; }
        public void setAssignmentType(TeachingAssignment.AssignmentType assignmentType) { this.assignmentType = assignmentType; }
    }

    public static class ScheduleDetailDTO {
        private Long classroomId;
        private Long numberOfSeats;
        private String lesson;
        private String dateTime;
        private String endDate;
        private String classType;

        public Long getClassroomId() { return classroomId; }
        public void setClassroomId(Long classroomId) { this.classroomId = classroomId; }
        public Long getNumberOfSeats() { return numberOfSeats; }
        public void setNumberOfSeats(Long numberOfSeats) { this.numberOfSeats = numberOfSeats; }
        public String getLesson() { return lesson; }
        public void setLesson(String lesson) { this.lesson = lesson; }
        public String getDateTime() { return dateTime; }
        public void setDateTime(String dateTime) { this.dateTime = dateTime; }
        public String getEndDate() { return endDate; }
        public void setEndDate(String endDate) { this.endDate = endDate; }
        public String getClassType() { return classType; }
        public void setClassType(String classType) { this.classType = classType; }
    }

    public static class MaterialDTO {
        private String lt;
        private String th;

        public String getLt() { return lt; }
        public void setLt(String lt) { this.lt = lt; }
        public String getTh() { return th; }
        public void setTh(String th) { this.th = th; }
    }

    public Long getScheduleId() { return scheduleId; }
    public void setScheduleId(Long scheduleId) { this.scheduleId = scheduleId; }
    public TeachingAssignmentDTO getAssignment() { return assignment; }
    public void setAssignment(TeachingAssignmentDTO assignment) { this.assignment = assignment; }
    public Long getClassroomId() { return classroomId; }
    public void setClassroomId(Long classroomId) { this.classroomId = classroomId; }
    public LocalDateTime getDateTime() { return dateTime; }
    public void setDateTime(LocalDateTime dateTime) { this.dateTime = dateTime; }
    public LocalDateTime getEndDate() { return endDate; }
    public void setEndDate(LocalDateTime endDate) { this.endDate = endDate; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public List<ScheduleDetailDTO> getScheduleDetails() { return scheduleDetails; }
    public void setScheduleDetails(List<ScheduleDetailDTO> scheduleDetails) { this.scheduleDetails = scheduleDetails; }
    public List<MaterialDTO> getMaterials() { return materials; }
    public void setMaterials(List<MaterialDTO> materials) { this.materials = materials; }
}