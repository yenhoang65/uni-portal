package io.spring.uni_portal.model;

import jakarta.persistence.*;

@Entity
@Table(name = "major")
public class Major {

    @Id
    @Column(name = "major_id")
    private Long majorId;

    @Column(name = "major_name", nullable = false)
    private String majorName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "faculty_id", nullable = false)
    private Faculty faculty;

    // Constructors
    public Major() {
    }

    public Major(String majorName, Faculty faculty) {
        this.majorName = majorName;
        this.faculty = faculty;
    }

    // Getters and Setters
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

    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    // Optional: toString()
    @Override
    public String toString() {
        return "Major{" +
                "majorId=" + majorId +
                ", majorName='" + majorName + '\'' +
                ", faculty=" + (faculty != null ? faculty.getFacultyName() : null) + // Chỉ hiển thị tên khoa
                '}';
    }
}