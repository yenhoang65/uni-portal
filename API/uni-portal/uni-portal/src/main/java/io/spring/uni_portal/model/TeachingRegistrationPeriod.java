package io.spring.uni_portal.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "teaching_registration_period")
public class TeachingRegistrationPeriod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "school_year", nullable = false)
    private Long schoolYear;

    @Column(name = "semester", nullable = false)
    private Long semester;

    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDateTime endDate;

    @Column(name = "status")
    private String status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSchoolYear() {
        return schoolYear;
    }

    public void setSchoolYear(Long schoolYear) {
        this.schoolYear = schoolYear;
    }

    public Long getSemester() {
        return semester;
    }

    public void setSemester(Long semester) {
        this.semester = semester;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
