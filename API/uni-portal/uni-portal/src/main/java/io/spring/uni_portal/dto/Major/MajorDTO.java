package io.spring.uni_portal.dto.Major;

import java.time.LocalDate;

public class MajorDTO {
    private Long majorId;
    private String majorName;
    private Long facultyId;
    private String facultyName;
    private LocalDate majorDateOfEstablishment;
    private String majorDescription;
    private String majorStatus;


    public Long getMajorId() {
        return majorId;
    }

    public void setMajorId(Long majorId) {
        this.majorId = majorId;
    }

    public String getMajorName() {
        return majorName;
    }

    public void setMajorName(String majorName) {
        this.majorName = majorName;
    }

    public Long getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(Long facultyId) {
        this.facultyId = facultyId;
    }

    public String getFacultyName() {
        return facultyName;
    }

    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }

    public LocalDate getMajorDateOfEstablishment() {
        return majorDateOfEstablishment;
    }

    public void setMajorDateOfEstablishment(LocalDate majorDateOfEstablishment) {
        this.majorDateOfEstablishment = majorDateOfEstablishment;
    }

    public String getMajorDescription() {
        return majorDescription;
    }

    public void setMajorDescription(String majorDescription) {
        this.majorDescription = majorDescription;
    }

    public String getMajorStatus() {
        return majorStatus;
    }

    public void setMajorStatus(String majorStatus) {
        this.majorStatus = majorStatus;
    }
}
