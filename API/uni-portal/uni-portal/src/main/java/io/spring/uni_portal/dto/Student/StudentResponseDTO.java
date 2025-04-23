package io.spring.uni_portal.dto.Student;

import io.spring.uni_portal.model.Student;

import java.time.LocalDate;

public class StudentResponseDTO {
    private Long userId;
    private String userName;
    private String gender;
    private String phoneNumber;
    private String email;
    private LocalDate dateOfBirth;
    private String educationLevel;
    private String typeOfTraining;
    private String specializationName;
    private Long classId;
    private String status;

    public StudentResponseDTO(Student student) {
        this.userId = student.getUser().getUserId();
        this.userName = student.getUser().getUserName();
        this.gender = student.getUser().getGender();
        this.phoneNumber = student.getUser().getPhoneNumber();
        this.email = student.getUser().getEmail();
        this.status = student.getUser().getStatus();
        this.dateOfBirth = student.getUser().getDateOfBirth();
        this.educationLevel = student.getEducationLevel();
        this.typeOfTraining = student.getTypeOfTraining();
        this.specializationName = student.getSpecialization().getSpecializationName();
        this.classId = student.getClassEntity() != null ? student.getClassEntity().getClassId() : null;

    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getEducationLevel() {
        return educationLevel;
    }

    public void setEducationLevel(String educationLevel) {
        this.educationLevel = educationLevel;
    }

    public String getTypeOfTraining() {
        return typeOfTraining;
    }

    public void setTypeOfTraining(String typeOfTraining) {
        this.typeOfTraining = typeOfTraining;
    }

    public String getSpecializationName() {
        return specializationName;
    }

    public void setSpecializationName(String specializationName) {
        this.specializationName = specializationName;
    }

    public Long getClassId() {
        return classId;
    }

    public void setClassId(Long classId) {
        this.classId = classId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
