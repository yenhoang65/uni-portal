package io.spring.uni_portal.service.TeachingScheduleRequestService;

import io.spring.uni_portal.dto.TeachingAssignment.TeachingAssignmentDetails;
import io.spring.uni_portal.dto.TeachingAssignment.TeachingAssignmentDetailsResponse;
import io.spring.uni_portal.dto.TeachingAssignment.TeachingAssignmentResponseDTO;
import io.spring.uni_portal.dto.TeachingScheduleRequest.*;
import io.spring.uni_portal.model.TeachingScheduleRequest;
import io.spring.uni_portal.response.Response;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ITeachingScheduleRequestService {
    Page<TeachingScheduleWithAssignmentResponseDTO> getAllTeachingSchedules(Pageable pageable, String searchValue);

    Page<TeachingScheduleDetailResponseDTO> getSchedulesByStatusAndUser(
            Long userId,
            List<String> statuses,
            Pageable pageable,
            String searchValue
    );
    List<TeachingScheduleDetailResponseDTO> getSchedulesWithSuccessStatus(Long userId);

    List<TeachingScheduleStatusResponseDTO> filterByStatuses(List<String> statuses);

}
