package io.spring.uni_portal.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "exam_schedule")
public class ExamSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "exam_schedule_id")
    private Long examScheduleId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "class_student_id")
    private ClassStudent classStudent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "classroom_id")
    private Classroom classroom;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "start_time")
    private String startTime;

    @Column(name = "end_time")
    private String endTime;

    @Column(name = "exam_form")
    private String examForm;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "grade_type_id")
    private GradeType gradeType;


    public ExamSchedule() {
    }

    // Constructor đầy đủ tham số
    public ExamSchedule(ClassStudent classStudent, Classroom classroom, LocalDate startDate, LocalDate endDate,
                        String startTime, String endTime, String examForm, GradeType gradeType) {
        this.classStudent = classStudent;
        this.classroom = classroom;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.examForm = examForm;
        this.gradeType = gradeType;
    }


    // Getters and Setters
    public Long getExamScheduleId() {
        return examScheduleId;
    }

    public void setExamScheduleId(Long examScheduleId) {
        this.examScheduleId = examScheduleId;
    }

    public ClassStudent getClassStudent() {
        return classStudent;
    }

    public void setClassStudent(ClassStudent classStudent) {
        this.classStudent = classStudent;
    }

    public Classroom getClassroom() {
        return classroom;
    }

    public void setClassroom(Classroom classroom) {
        this.classroom = classroom;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getExamForm() {
        return examForm;
    }

    public void setExamForm(String examForm) {
        this.examForm = examForm;
    }

    public GradeType getGradeType() {
        return gradeType;
    }

    public void setGradeType(GradeType gradeType) {
        this.gradeType = gradeType;
    }

}
