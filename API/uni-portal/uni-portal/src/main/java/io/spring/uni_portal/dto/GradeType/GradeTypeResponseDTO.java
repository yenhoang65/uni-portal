package io.spring.uni_portal.dto.GradeType;


public class GradeTypeResponseDTO {
    private Long gradeTypeId;
    private String code;
    private String name;
    private Double coefficient;

    // Getters and Setters
    public Long getGradeTypeId() {
        return gradeTypeId;
    }
    public void setGradeTypeId(Long gradeTypeId) {
        this.gradeTypeId = gradeTypeId;
    }
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Double getCoefficient() {
        return coefficient;
    }
    public void setCoefficient(Double coefficient) {
        this.coefficient = coefficient;
    }
}
