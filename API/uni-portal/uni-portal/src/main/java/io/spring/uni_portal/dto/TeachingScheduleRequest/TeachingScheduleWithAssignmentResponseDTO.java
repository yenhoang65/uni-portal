package io.spring.uni_portal.dto.TeachingScheduleRequest;

import io.spring.uni_portal.model.*;

public class TeachingScheduleWithAssignmentResponseDTO {

    private Long scheduleId;
    private Long classroomId;
    private String lesson;
    private String dateTime;
    private String endDate;
    private String classType;
    private TeachingAssignmentDTO assignment;  // Thêm thông tin về TeachingAssignment

    // Ánh xạ từ entity TeachingScheduleRequest sang DTO
    public TeachingScheduleWithAssignmentResponseDTO(TeachingScheduleRequest scheduleRequest) {
        this.scheduleId = scheduleRequest.getScheduleId();
        this.classroomId = scheduleRequest.getClassroom().getClassroomId();
        this.lesson = scheduleRequest.getLesson();
        this.dateTime = scheduleRequest.getDateTime().toString();
        this.endDate = scheduleRequest.getEndDate().toString();
        this.classType = scheduleRequest.getClassType();

        // Ánh xạ TeachingAssignment vào DTO
        TeachingAssignment assignment = scheduleRequest.getAssignment();
        this.assignment = new TeachingAssignmentDTO(assignment);
    }

    // Getters and setters
    public Long getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Long scheduleId) {
        this.scheduleId = scheduleId;
    }

    public Long getClassroomId() {
        return classroomId;
    }

    public void setClassroomId(Long classroomId) {
        this.classroomId = classroomId;
    }

    public String getLesson() {
        return lesson;
    }

    public void setLesson(String lesson) {
        this.lesson = lesson;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getClassType() {
        return classType;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }

    public TeachingAssignmentDTO getAssignment() {
        return assignment;
    }

    public void setAssignment(TeachingAssignmentDTO assignment) {
        this.assignment = assignment;
    }

    // TeachingAssignmentDTO class
    public static class TeachingAssignmentDTO {

        private Long assignmentId;
        private LecturerDTO lecturer;  // Thêm thông tin giảng viên
        private SubjectDTO subject;    // Thêm thông tin môn học
        private TermClassDTO termClass; // Thêm thông tin lớp học

        public TeachingAssignmentDTO(TeachingAssignment assignment) {
            this.assignmentId = assignment.getAssignmentId();
            this.lecturer = new LecturerDTO(assignment.getLecturer());
            this.subject = new SubjectDTO(assignment.getSubject());
            this.termClass = new TermClassDTO(assignment.getTermClass());
        }

        // Getters and setters for TeachingAssignmentDTO
        public Long getAssignmentId() {
            return assignmentId;
        }

        public void setAssignmentId(Long assignmentId) {
            this.assignmentId = assignmentId;
        }

        public LecturerDTO getLecturer() {
            return lecturer;
        }

        public void setLecturer(LecturerDTO lecturer) {
            this.lecturer = lecturer;
        }

        public SubjectDTO getSubject() {
            return subject;
        }

        public void setSubject(SubjectDTO subject) {
            this.subject = subject;
        }

        public TermClassDTO getTermClass() {
            return termClass;
        }

        public void setTermClass(TermClassDTO termClass) {
            this.termClass = termClass;
        }

        // LecturerDTO class
        public static class LecturerDTO {
            private Long lecturerId;
            private String lecturerName;

            public LecturerDTO(Lecturer lecturer) {
                this.lecturerId = lecturer.getUserId();
                this.lecturerName = lecturer.getUser().getUserName();
            }

            // Getters and setters for LecturerDTO
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
        }

        // SubjectDTO class
        public static class SubjectDTO {
            private Long subjectId;
            private String subjectName;
            private Integer ltCredits;
            private Integer thCredits;

            public SubjectDTO(Subject subject) {
                this.subjectId = subject.getSubjectId();
                this.subjectName = subject.getSubjectName();
                this.ltCredits = subject.getLtCredits();
                this.thCredits = subject.getThCredits();
            }

            // Getters and setters for SubjectDTO
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

            public Integer getLtCredits() {
                return ltCredits;
            }

            public void setLtCredits(Integer ltCredits) {
                this.ltCredits = ltCredits;
            }

            public Integer getThCredits() {
                return thCredits;
            }

            public void setThCredits(Integer thCredits) {
                this.thCredits = thCredits;
            }
        }

        // TermClassDTO class
        public static class TermClassDTO {
            private Long termClassId;
            private String termName;

            public TermClassDTO(TermClass termClass) {
                this.termClassId = termClass.getTermclassId();
                this.termName = termClass.getClassname();
            }

            // Getters and setters for TermClassDTO
            public Long getTermClassId() {
                return termClassId;
            }

            public void setTermClassId(Long termClassId) {
                this.termClassId = termClassId;
            }

            public String getTermName() {
                return termName;
            }

            public void setTermName(String termName) {
                this.termName = termName;
            }
        }
    }
}

