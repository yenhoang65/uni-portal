package io.spring.uni_portal.dto.TermClass;

public class TermClassResponse {
    private Long termclassId;
    private String classname;
    private String progress;
    private String semester;
    private String schoolyears;

    // Getters and Setters


    public Long getTermclassId() {
        return termclassId;
    }

    public void setTermclassId(Long termclassId) {
        this.termclassId = termclassId;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getSchoolyears() {
        return schoolyears;
    }

    public void setSchoolyears(String schoolyears) {
        this.schoolyears = schoolyears;
    }
}
