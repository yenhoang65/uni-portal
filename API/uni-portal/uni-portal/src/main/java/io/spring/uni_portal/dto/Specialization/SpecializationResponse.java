package io.spring.uni_portal.dto.Specialization;

import java.time.LocalDate;

public class SpecializationResponse {
    private Long specializationId;
    private String specializationName;
    private Long majorId;
    private String majorName;
    private LocalDate specializationDateOfEstablishment;
    private String specializationDescription;
    private String specializationStatus;

    public SpecializationResponse() {}

    public SpecializationResponse(Long specializationId, String specializationName, Long majorId, String majorName,
                                  LocalDate specializationDateOfEstablishment, String specializationDescription, String specializationStatus) {
        this.specializationId = specializationId;
        this.specializationName = specializationName;
        this.majorId = majorId;
        this.majorName = majorName;
        this.specializationDateOfEstablishment = specializationDateOfEstablishment;
        this.specializationDescription = specializationDescription;
        this.specializationStatus = specializationStatus;
    }

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

    public String getMajorName() {
        return majorName;
    }

    public void setMajorName(String majorName) {
        this.majorName = majorName;
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