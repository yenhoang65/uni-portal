package io.spring.uni_portal.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "faculty")
public class Faculty {

    @Id
    @Column(name = "faculty_id")
    private Long facultyId;

    @Column(name = "faculty_name", nullable = false)
    private String facultyName;

    @Column(name = "faculty_Date_of_establishment")
    private LocalDate facultyDateOfEstablishment;

    @Column(name = "faculty_email")
    private String facultyEmail;

    @Column(name = "faculty_phoneNumber")
    private String facultyPhoneNumber;

    @Column(name = "faculty_address")
    private String facultyAddress;

    @Column(name = "faculty_description")
    private String facultyDescription;

    @Column(name = "faculty_logo")
    private String facultyLogo;

    @Column(name = "faculty_status")
    private String facultyStatus;

    public Faculty() {}

    public Faculty(String facultyName) {
        this.facultyName = facultyName;
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

    public LocalDate getFacultyDateOfEstablishment() {
        return facultyDateOfEstablishment;
    }

    public void setFacultyDateOfEstablishment(LocalDate facultyDateOfEstablishment) {
        this.facultyDateOfEstablishment = facultyDateOfEstablishment;
    }

    public String getFacultyEmail() {
        return facultyEmail;
    }

    public void setFacultyEmail(String facultyEmail) {
        this.facultyEmail = facultyEmail;
    }

    public String getFacultyPhoneNumber() {
        return facultyPhoneNumber;
    }

    public void setFacultyPhoneNumber(String facultyPhoneNumber) {
        this.facultyPhoneNumber = facultyPhoneNumber;
    }

    public String getFacultyAddress() {
        return facultyAddress;
    }

    public void setFacultyAddress(String facultyAddress) {
        this.facultyAddress = facultyAddress;
    }

    public String getFacultyDescription() {
        return facultyDescription;
    }

    public void setFacultyDescription(String facultyDescription) {
        this.facultyDescription = facultyDescription;
    }

    public String getFacultyLogo() {
        return facultyLogo;
    }

    public void setFacultyLogo(String facultyLogo) {
        this.facultyLogo = facultyLogo;
    }

    public String getFacultyStatus() {
        return facultyStatus;
    }

    public void setFacultyStatus(String facultyStatus) {
        this.facultyStatus = facultyStatus;
    }

    // Optional: toString()
    @Override
    public String toString() {
        return "Faculty{" +
                "facultyId=" + facultyId +
                ", facultyName='" + facultyName + '\'' +
                ", facultyDateOfEstablishment=" + facultyDateOfEstablishment +
                ", facultyEmail='" + facultyEmail + '\'' +
                ", facultyPhoneNumber='" + facultyPhoneNumber + '\'' +
                ", facultyAddress='" + facultyAddress + '\'' +
                ", facultyDescription='" + facultyDescription + '\'' +
                ", facultyLogo='" + facultyLogo + '\'' +
                ", facultyStatus='" + facultyStatus + '\'' +
                '}';
    }

}
