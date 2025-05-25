package io.spring.uni_portal.model;

import jakarta.persistence.*;

@Entity
@Table(name = "grade_type")
public class GradeType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "grade_type_id")
    private Long gradeTypeId;

    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "coefficient",nullable = false)
    private Double coefficient;

    // Constructors
    public GradeType() {}

    public GradeType(String code, String name, Double coefficient) {
        this.code = code;
        this.name = name;
        this.coefficient = coefficient;
    }

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
