package io.spring.uni_portal.service.TeachingAssignmentService;

import io.spring.uni_portal.dto.TeachingAssignment.TeachingAssignmentDTO;
import io.spring.uni_portal.dto.TeachingAssignment.TeachingAssignmentResponse;
import io.spring.uni_portal.dto.TermClass.TermClassResponseUnassignedClasses;
import io.spring.uni_portal.model.TeachingAssignment;
import io.spring.uni_portal.response.Response;

import java.util.List;

public interface ITeachingAssignmentService {

    Response<TeachingAssignmentResponse> assignClassToLecturer(TeachingAssignmentDTO dto);

    Response<List<TeachingAssignmentResponse>> getAllAssignments();

    Response<TeachingAssignmentResponse> getAssignmentById(Long assignmentId);

    Response<TeachingAssignmentResponse> updateAssignment(Long assignmentId, TeachingAssignmentDTO dto);

    Response<Void> deleteAssignment(Long assignmentId);

    Response<List<TeachingAssignmentResponse>> getAssignedClasses();

    Response<List<TermClassResponseUnassignedClasses>> getUnassignedClasses();
}
