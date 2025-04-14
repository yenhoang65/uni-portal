package io.spring.uni_portal.model;

import jakarta.persistence.*;

@Entity
@Table(name = "lecturer")
public class Lecturer {

    @Id
    @Column(name = "user_id")
    private Long userId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "academic_degree")
    private String academicDegree;

    @Column(name = "graduated_from")
    private String graduatedFrom;

    private String position;

    @ManyToOne
    @JoinColumn(name = "major_id")
    private Major major;

    // Constructors
    public Lecturer() {}

    public Lecturer(User user, String academicDegree, String graduatedFrom, String position, Major major) {
        this.user = user;
        this.academicDegree = academicDegree;
        this.graduatedFrom = graduatedFrom;
        this.position = position;
        this.major = major;
    }

    // Getters and Setters
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getAcademicDegree() {
        return academicDegree;
    }

    public void setAcademicDegree(String academicDegree) {
        this.academicDegree = academicDegree;
    }

    public String getGraduatedFrom() {
        return graduatedFrom;
    }

    public void setGraduatedFrom(String graduatedFrom) {
        this.graduatedFrom = graduatedFrom;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Major getMajor() {
        return major;
    }

    public void setMajor(Major major) {
        this.major = major;
    }

    @Override
    public String toString() {
        return "Lecturer{" +
                "userId=" + userId +
                ", academicDegree='" + academicDegree + '\'' +
                ", graduatedFrom='" + graduatedFrom + '\'' +
                ", position='" + position + '\'' +
                ", major=" + (major != null ? major.getMajorName() : null) +
                '}';
    }
}
