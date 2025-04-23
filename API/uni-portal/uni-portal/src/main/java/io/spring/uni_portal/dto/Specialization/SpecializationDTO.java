package io.spring.uni_portal.dto.Specialization;

import java.time.LocalDate;

public class SpecializationDTO {
    private Long specializationId;
    private String specializationName;
    private Long majorId;
    private LocalDate specializationDateOfEstablishment;
    private String specializationDescription;
    private String specializationStatus;


    public Long getSpecializationId() {
        return specializationId;
    }

    public void setSpecializationId(Long specializationId) {
        this.specializationId = specializationId;
    }

    public String getSpecializationName() {
        return specializationName;
    }

    public void setSpecializationName(String specializationName) {
        this.specializationName = specializationName;
    }

    public Long getMajorId() {
        return majorId;
    }

    public void setMajorId(Long majorId) {
        this.majorId = majorId;
    }

    public LocalDate getSpecializationDateOfEstablishment() {
        return specializationDateOfEstablishment;
    }

    public void setSpecializationDateOfEstablishment(LocalDate specializationDateOfEstablishment) {
        this.specializationDateOfEstablishment = specializationDateOfEstablishment;
    }

    public String getSpecializationDescription() {
        return specializationDescription;
    }

    public void setSpecializationDescription(String specializationDescription) {
        this.specializationDescription = specializationDescription;
    }

    public String getSpecializationStatus() {
        return specializationStatus;
    }

    public void setSpecializationStatus(String specializationStatus) {
        this.specializationStatus = specializationStatus;
    }
}
