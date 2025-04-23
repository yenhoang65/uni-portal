package io.spring.uni_portal.dto.TeachingRegistrationPeriod;

import java.time.LocalDateTime;

public class TeachingRegistrationPeriodDTO {
    private Long id;
    private Long schoolYear;
    private Long semester;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
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
