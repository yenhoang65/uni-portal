package io.spring.uni_portal.dto.Lecturer;

import io.spring.uni_portal.model.Lecturer;

import java.time.LocalDate;

public class LecturerResponseDTO {
    // User fields
    private Long userId;
    private String userName;
    private String gender;
    private String phoneNumber;
    private String address;
    private String ethnicGroup;
    private LocalDate dateOfBirth;
    private String religion;
    private String idNumber;
    private String email;
    private String placeOfBirth;
    private String permanentResident;
    private String bank;
    private String bankAccountOwner;
    private String bankAccountNumber;
    private String role;
    private LocalDate admissionDate;
    private String status;
    // Lecturer fields
    private String academicDegree;
    private String graduatedFrom;
    private String position;

    // Major + Faculty
    private Long majorId;
    private String majorName;
    private String facultyName;

    public LecturerResponseDTO() {}

    public LecturerResponseDTO(Lecturer lecturer) {
        this.userId = lecturer.getUserId();
        this.userName = lecturer.getUser().getUserName();
        this.gender = lecturer.getUser().getGender();
        this.phoneNumber = lecturer.getUser().getPhoneNumber();
        this.address = lecturer.getUser().getAddress();
        this.ethnicGroup = lecturer.getUser().getEthnicGroup();
        this.dateOfBirth = lecturer.getUser().getDateOfBirth();
        this.religion = lecturer.getUser().getReligion();
        this.idNumber = lecturer.getUser().getIdNumber();
        this.email = lecturer.getUser().getEmail();
        this.placeOfBirth = lecturer.getUser().getPlaceOfBirth();
        this.permanentResident = lecturer.getUser().getPermanentResident();
        this.bank = lecturer.getUser().getBank();
        this.bankAccountOwner = lecturer.getUser().getBankAccountOwner();
        this.bankAccountNumber = lecturer.getUser().getBankAccountNumber();
        this.role = lecturer.getUser().getRole();
        this.admissionDate = lecturer.getUser().getAdmissionDate();
        this.status = lecturer.getUser().getStatus();
        this.academicDegree = lecturer.getAcademicDegree();
        this.graduatedFrom = lecturer.getGraduatedFrom();
        this.position = lecturer.getPosition();

        this.majorId = lecturer.getMajor().getMajorId();
        this.majorName = lecturer.getMajor().getMajorName();
        this.facultyName = lecturer.getMajor().getFaculty().getFacultyName(); // phải có getFaculty()
    }

    // Getters and Setters
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getEthnicGroup() { return ethnicGroup; }
    public void setEthnicGroup(String ethnicGroup) { this.ethnicGroup = ethnicGroup; }

    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; }

    public String getReligion() { return religion; }
    public void setReligion(String religion) { this.religion = religion; }

    public String getIdNumber() { return idNumber; }
    public void setIdNumber(String idNumber) { this.idNumber = idNumber; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPlaceOfBirth() { return placeOfBirth; }
    public void setPlaceOfBirth(String placeOfBirth) { this.placeOfBirth = placeOfBirth; }

    public String getPermanentResident() { return permanentResident; }
    public void setPermanentResident(String permanentResident) { this.permanentResident = permanentResident; }

    public String getBank() { return bank; }
    public void setBank(String bank) { this.bank = bank; }

    public String getBankAccountOwner() { return bankAccountOwner; }
    public void setBankAccountOwner(String bankAccountOwner) { this.bankAccountOwner = bankAccountOwner; }

    public String getBankAccountNumber() { return bankAccountNumber; }
    public void setBankAccountNumber(String bankAccountNumber) { this.bankAccountNumber = bankAccountNumber; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public LocalDate getAdmissionDate() { return admissionDate; }
    public void setAdmissionDate(LocalDate admissionDate) { this.admissionDate = admissionDate; }

    public String getAcademicDegree() { return academicDegree; }
    public void setAcademicDegree(String academicDegree) { this.academicDegree = academicDegree; }

    public String getGraduatedFrom() { return graduatedFrom; }
    public void setGraduatedFrom(String graduatedFrom) { this.graduatedFrom = graduatedFrom; }

    public String getPosition() { return position; }
    public void setPosition(String position) { this.position = position; }

    public Long getMajorId() { return majorId; }
    public void setMajorId(Long majorId) { this.majorId = majorId; }

    public String getMajorName() { return majorName; }
    public void setMajorName(String majorName) { this.majorName = majorName; }

    public String getFacultyName() { return facultyName; }
    public void setFacultyName(String facultyName) { this.facultyName = facultyName; }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
