package io.spring.uni_portal.dto.Faculty;

import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

public class FacultyDTO {
    private Long facultyId;
    private String facultyName;
    private LocalDate facultyDateOfEstablishment;
    private String facultyEmail;
    private String facultyPhoneNumber;
    private String facultyAddress;
    private String facultyDescription;
    private String facultyLogo;
    private String facultyStatus;
    private MultipartFile facultyLogoFile;

    // Getters and Setters
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

    public MultipartFile getFacultyLogoFile() {
        return facultyLogoFile;
    }

    public void setFacultyLogoFile(MultipartFile facultyLogoFile) {
        this.facultyLogoFile = facultyLogoFile;
    }
}
