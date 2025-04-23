package io.spring.uni_portal.service.TeachingAssignmentService;

import io.spring.uni_portal.dto.TeachingAssignment.TeachingAssignmentDTO;
import io.spring.uni_portal.dto.TeachingAssignment.TeachingAssignmentResponse;
import io.spring.uni_portal.model.TeachingAssignment;

import java.util.List;

public interface ITeachingAssignmentService {
    List<TeachingAssignmentResponse> getAll();
    TeachingAssignmentResponse create(TeachingAssignmentDTO dto);
    TeachingAssignment update(Long id, TeachingAssignmentDTO dto);
    void delete(Long id);
    List<TeachingAssignment> getByLecturer(Long userId, Long schoolYear, Long semester);
}
