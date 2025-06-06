package io.spring.uni_portal.model;

import jakarta.persistence.*;

@Entity
@Table(name = "term_class")
public class TermClass {

    @Id
    @Column(name = "termclass_id")
    private Long termclassId;

    @Column(name = "classname") // Thêm trường classname
    private String classname;

    @Column(name = "progress")
    private String progress;

    @Column(name = "semester")
    private String semester;

    @Column(name = "schoolyears")
    private String schoolyears;

    // Constructors
    public TermClass() {
    }

    public TermClass(String classname, String progress, String semester, String schoolyears) {
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
