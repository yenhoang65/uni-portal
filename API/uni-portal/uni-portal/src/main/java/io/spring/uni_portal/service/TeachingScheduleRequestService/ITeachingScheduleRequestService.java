package io.spring.uni_portal.service.TeachingScheduleRequestService;

import io.spring.uni_portal.dto.TeachingAssignment.TeachingAssignmentDetails;
import io.spring.uni_portal.dto.TeachingAssignment.TeachingAssignmentDetailsResponse;
import io.spring.uni_portal.dto.TeachingAssignment.TeachingAssignmentResponseDTO;
import io.spring.uni_portal.dto.TeachingScheduleRequest.TeachingScheduleRequestDTO;
import io.spring.uni_portal.model.TeachingScheduleRequest;
import io.spring.uni_portal.response.Response;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface ITeachingScheduleRequestService {
    // Lấy các lớp học mà giảng viên đã phân công
    Response<List<TeachingAssignmentResponseDTO>> getAssignedClasses(HttpServletRequest request);

    // Đăng ký lịch dạy cho lớp học đã phân công
//    Response<String> registerTeachingSchedule(HttpServletRequest request, Long assignmentId, TeachingScheduleRequestDTO teachingScheduleRequestDTO);

}
