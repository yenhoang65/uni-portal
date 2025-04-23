package io.spring.uni_portal.model;

import jakarta.persistence.*;

@Entity
@Table(name = "classroom")
public class Classroom {

    @Id
    @Column(name = "classroom_id")
    private Long classroomId;

    @Column(name = "classroom_name")
    private String classroomName;

    @Column(name = "number_of_seats")
    private Long numberOfSeats;

    @Column(name = "device")
    private String device;

    @Column(name = "status")
    private String status;

    public Classroom() {}

    public Classroom(Long classroomId, String classroomName, Long numberOfSeats, String device,String status) {
        this.classroomId = classroomId;
        this.classroomName = classroomName;
        this.numberOfSeats = numberOfSeats;
        this.device = device;
        this.status = status;
    }

    // Getters and Setters
    public Long getClassroomId() {
        return classroomId;
    }

    public void setClassroomId(Long classroomId) {
        this.classroomId = classroomId;
    }

    public String getClassroomName() {
        return classroomName;
    }

    public void setClassroomName(String classroomName) {
        this.classroomName = classroomName;
    }

    public Long getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(Long numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
