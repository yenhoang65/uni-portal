package io.spring.uni_portal.dto.User;

import java.time.LocalDate;

public class UserProfileUpdateDTO {

    // Các trường của bảng User
    private String userName;
    private String email;
    private String phoneNumber;
    private String address;
    private String religion;
    private LocalDate dateOfBirth;
    private String ethnicGroup;
    private String idNumber;
    private String placeOfBirth;
    private String permanentResident;
    private String bank;
    private String bankAccountOwner;
    private String bankAccountNumber;
    private String status;

    // Các trường của bảng Student
    private String educationLevel;

    // Các trường của bảng Lecturer
    private String academicDegree;
    private String position;

    // Các trường bổ sung từ bảng Major và Faculty cho Lecturer
    private String majorName;
    private String facultyName;

    // Constructors
    public UserProfileUpdateDTO(String userName,String email, String phoneNumber, String address, String religion, LocalDate dateOfBirth,
                                String ethnicGroup, String idNumber, String placeOfBirth, String permanentResident,
                                String bank, String bankAccountOwner, String bankAccountNumber, String status,
                                String educationLevel, String academicDegree, String position, String majorName, String facultyName) {
        this.userName = userName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.religion = religion;
        this.dateOfBirth = dateOfBirth;
        this.ethnicGroup = ethnicGroup;
        this.idNumber = idNumber;
        this.placeOfBirth = placeOfBirth;
        this.permanentResident = permanentResident;
        this.bank = bank;
        this.bankAccountOwner = bankAccountOwner;
        this.bankAccountNumber = bankAccountNumber;
        this.status = status;
        this.educationLevel = educationLevel;
        this.academicDegree = academicDegree;
        this.position = position;
        this.majorName = majorName;
        this.facultyName = facultyName;
    }

    // Getters and Setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getEthnicGroup() {
        return ethnicGroup;
    }

    public void setEthnicGroup(String ethnicGroup) {
        this.ethnicGroup = ethnicGroup;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getPlaceOfBirth() {
        return placeOfBirth;
    }

    public void setPlaceOfBirth(String placeOfBirth) {
        this.placeOfBirth = placeOfBirth;
    }

    public String getPermanentResident() {
        return permanentResident;
    }

    public void setPermanentResident(String permanentResident) {
        this.permanentResident = permanentResident;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getBankAccountOwner() {
        return bankAccountOwner;
    }

    public void setBankAccountOwner(String bankAccountOwner) {
        this.bankAccountOwner = bankAccountOwner;
    }

    public String getBankAccountNumber() {
        return bankAccountNumber;
    }

    public void setBankAccountNumber(String bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEducationLevel() {
        return educationLevel;
    }

    public void setEducationLevel(String educationLevel) {
        this.educationLevel = educationLevel;
    }

    public String getAcademicDegree() {
        return academicDegree;
    }

    public void setAcademicDegree(String academicDegree) {
        this.academicDegree = academicDegree;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getMajorName() {
        return majorName;
    }

    public void setMajorName(String majorName) {
        this.majorName = majorName;
    }

    public String getFacultyName() {
        return facultyName;
    }

    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }
}
