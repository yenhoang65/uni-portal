package io.spring.uni_portal.dto.GradeType;

public class GradeTypeEventDTO {
    private Long id;
    private String code;
    private String name;
    private Double coefficient;

    public GradeTypeEventDTO(Long id, String code, String name, Double coefficient) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.coefficient = coefficient;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Double getCoefficient() { return coefficient; }
    public void setCoefficient(Double coefficient) { this.coefficient = coefficient; }
}
