package io.spring.uni_portal.dto.Specialization;

public class SpecializationResponse {
    private Long specializationId;
    private String specializationName;
    private Long majorId;
    private String majorName;

    public SpecializationResponse() {}

    public SpecializationResponse(Long specializationId, String specializationName, Long majorId, String majorName) {
        this.specializationId = specializationId;
        this.specializationName = specializationName;
        this.majorId = majorId;
        this.majorName = majorName;
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
}