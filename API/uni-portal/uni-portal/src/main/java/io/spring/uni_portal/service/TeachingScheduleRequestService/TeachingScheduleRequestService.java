package io.spring.uni_portal.service.TeachingScheduleRequestService;

import io.spring.uni_portal.dto.TeachingAssignment.TeachingAssignmentDetails;
import io.spring.uni_portal.dto.TeachingAssignment.TeachingAssignmentDetailsResponse;
import io.spring.uni_portal.dto.TeachingAssignment.TeachingAssignmentResponseDTO;
import io.spring.uni_portal.dto.TeachingScheduleRequest.TeachingScheduleRequestDTO;
import io.spring.uni_portal.model.*;
import io.spring.uni_portal.repository.ClassroomRepository;
import io.spring.uni_portal.repository.TeachingAssignmentRepository;
import io.spring.uni_portal.repository.TeachingScheduleRequestRepository;
import io.spring.uni_portal.repository.TermClassRepository;
import io.spring.uni_portal.response.Response;
import io.spring.uni_portal.security.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class TeachingScheduleRequestService {

    @Autowired
    private TeachingAssignmentRepository teachingAssignmentRepository;

    @Autowired
    private TeachingScheduleRequestRepository teachingScheduleRequestRepository;

    @Autowired
    private JwtService jwtService;

    // API lấy lớp học đã phân công cho giảng viên
    public Response<List<TeachingAssignmentResponseDTO>> getAssignedClasses(HttpServletRequest request) {
        Long lecturerId = jwtService.getUserIdFromToken(request);

        if (lecturerId == null) {
            return Response.failure("Unauthorized");
        }

        // Sử dụng phương thức findByLecturer_UserId để lấy các lớp đã phân công
        List<TeachingAssignment> assignments = teachingAssignmentRepository.findByLecturer_UserId(lecturerId);

        List<TeachingAssignmentResponseDTO> response = assignments.stream()
                .map(assignment -> new TeachingAssignmentResponseDTO(
                        assignment.getAssignmentId(),
                        assignment.getLecturer().getUserId(),
                        assignment.getLecturer().getUser().getUserName(),
                        assignment.getSubject().getSubjectId(),
                        assignment.getSubject().getSubjectName(),
                        assignment.getTermClass().getTermclassId(),
                        assignment.getTermClass().getClassname(),
                        assignment.getTermClass().getProgress(),
                        assignment.getTermClass().getSemester(),
                        assignment.getTermClass().getSchoolyears()
                ))
                .collect(Collectors.toList());

        return Response.success("Classes assigned to lecturer", response);
    }

    // API đăng ký lịch dạy cho lớp học đã phân công
//    public Response<String> registerTeachingSchedule(HttpServletRequest request, Long assignmentId, TeachingScheduleRequestDTO teachingScheduleRequestDTO) {
//        Long lecturerId = jwtService.getUserIdFromToken(request);
//
//        if (lecturerId == null) {
//            return Response.failure("Unauthorized");
//        }
//
//        // Lấy thông tin phân công giảng dạy từ assignmentId
//        TeachingAssignment teachingAssignment = teachingAssignmentRepository.findById(assignmentId)
//                .orElseThrow(() -> new RuntimeException("Teaching assignment not found"));
//
//        // Kiểm tra xem giảng viên có quyền đăng ký lịch dạy cho lớp này
//        if (!teachingAssignment.getLecturer().getUserId().equals(lecturerId)) {
//            return Response.failure("You are not authorized to register schedule for this class.");
//        }
//
//        // Kiểm tra xem lớp học đã có lịch dạy chưa
//        TeachingScheduleRequest existingSchedule = teachingScheduleRequestRepository.findByAssignment(teachingAssignment);
//        if (existingSchedule != null) {
//            return Response.failure("This class already has a teaching schedule.");
//        }
//
//        // Tạo lịch dạy mới
//        TeachingScheduleRequest teachingScheduleRequest = new TeachingScheduleRequest();
//        teachingScheduleRequest.setAssignment(teachingAssignment);
//        teachingScheduleRequest.setDateTime(teachingScheduleRequestDTO.getDateTime());
//        teachingScheduleRequest.setStatus(1L); // Giả sử trạng thái là "Đã xác nhận"
//        teachingScheduleRequest.setClassType(teachingAssignment.getTermClass().getClassType());
//        teachingScheduleRequest.setCreatedAt(LocalDateTime.now());
//
//        // Lưu lịch dạy vào cơ sở dữ liệu
//        teachingScheduleRequestRepository.save(teachingScheduleRequest);
//
//        return Response.success("Teaching schedule registered successfully", null);
//    }
}