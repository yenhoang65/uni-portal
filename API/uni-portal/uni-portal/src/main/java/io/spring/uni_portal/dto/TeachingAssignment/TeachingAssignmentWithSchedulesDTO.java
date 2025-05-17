package io.spring.uni_portal.dto.TeachingAssignment;

import io.spring.uni_portal.dto.TeachingScheduleRequest.TeachingScheduleRequestResponseDTO;

import java.util.List;

public class TeachingAssignmentWithSchedulesDTO {
    private TeachingAssignmentSimpleDTO assignment;

    private List<TeachingScheduleSimpleDTO> schedules;


    public TeachingAssignmentSimpleDTO getAssignment() {
        return assignment;
    }

    public void setAssignment(TeachingAssignmentSimpleDTO assignment) {
        this.assignment = assignment;
    }


    public List<TeachingScheduleSimpleDTO> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<TeachingScheduleSimpleDTO> schedules) {
        this.schedules = schedules;
    }
}
