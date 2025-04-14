package io.spring.uni_portal.model;

import jakarta.persistence.*;

@Entity
@Table(name = "faculty")
public class Faculty {

    @Id
    @Column(name = "faculty_id")
    private Long facultyId;

    @Column(name = "faculty_name", nullable = false)
    private String facultyName;

    public Faculty() {}

    public Faculty(String facultyName) {
        this.facultyName = facultyName;
    }

    // Getters and Setters
    public Long getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(Long facultyId) {
        this.facultyId = facultyId;
    }

    public String getFacultyName() {
        return facultyName;
    }

    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }

    // Optional: toString()
    @Override
    public String toString() {
        return "Faculty{" +
                "facultyId=" + facultyId +
                ", facultyName='" + facultyName + '\'' +
                '}';
    }
}
