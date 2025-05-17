    package io.spring.uni_portal.service.TeachingAssignmentService;

    import io.spring.uni_portal.dto.TeachingAssignment.TeachingAssignmentDTO;
    import io.spring.uni_portal.dto.TeachingAssignment.TeachingAssignmentDetailDTO;
    import io.spring.uni_portal.dto.TeachingAssignment.TeachingAssignmentResponse;
    import io.spring.uni_portal.dto.TeachingAssignment.TeachingAssignmentWithSchedulesDTO;
    import io.spring.uni_portal.dto.TermClass.TermClassResponseUnassignedClasses;
    import io.spring.uni_portal.model.TeachingAssignment;
    import io.spring.uni_portal.response.Response;
    import org.springframework.data.domain.Page;
    import org.springframework.data.domain.Pageable;

    import java.util.List;
    import java.util.Optional;

    public interface ITeachingAssignmentService {

        Response<List<TeachingAssignmentResponse>> assignClassToLecturer(TeachingAssignmentDTO dto);

        Response<List<TeachingAssignmentResponse>> getAllAssignments();

        Response<TeachingAssignmentDetailDTO> getAssignmentById(Long assignmentId);


        Response<TeachingAssignmentResponse> updateAssignment(Long assignmentId, TeachingAssignmentDTO dto);

        Response<Void> deleteAssignment(Long assignmentId);

        Response<List<TeachingAssignmentResponse>> getAssignedClasses();

        Response<List<TermClassResponseUnassignedClasses>> getUnassignedClasses();

        Optional<TeachingAssignment> findById(Long id);

        Response<List<TeachingAssignmentResponse>> getAllAssignmentsForAdmin();

        Response<Page<TeachingAssignmentResponse>> getAssignmentsWithSearch(String searchValue, Pageable pageable);


        TeachingAssignmentWithSchedulesDTO getAssignmentWithSchedules(Long assignmentId);
    }
