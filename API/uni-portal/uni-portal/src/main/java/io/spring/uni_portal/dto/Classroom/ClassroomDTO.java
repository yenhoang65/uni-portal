package io.spring.uni_portal.dto.Classroom;

import java.util.List;

public class ClassroomDTO {
    private Long classroomId;
    private String classroomName;
    private Long numberOfSeats;
    private List<String> devices;
    private String status;

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

    public List<String> getDevices() {
        return devices;
    }

    public void setDevices(List<String> devices) {
        this.devices = devices;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}