package io.spring.uni_portal.dto.TermClass;

public class TermClassResponseUnassignedClasses {
    private Long termclassId;
    private String classname;
    private String progress;
    private String semester;
    private String schoolyears;

    // Constructor
    public TermClassResponseUnassignedClasses(Long termclassId, String classname, String progress, String semester, String schoolyears) {
        this.termclassId = termclassId;
        this.classname = classname;
        this.progress = progress;
        this.semester = semester;
        this.schoolyears = schoolyears;
    }

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
