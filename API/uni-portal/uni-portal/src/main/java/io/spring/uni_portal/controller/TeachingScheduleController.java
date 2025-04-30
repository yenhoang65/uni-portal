package io.spring.uni_portal.controller;

import io.spring.uni_portal.dto.TeachingAssignment.TeachingAssignmentDetails;
import io.spring.uni_portal.dto.TeachingAssignment.TeachingAssignmentDetailsResponse;
import io.spring.uni_portal.dto.TeachingAssignment.TeachingAssignmentResponseDTO;
import io.spring.uni_portal.dto.TeachingScheduleRequest.TeachingScheduleRequestDTO;
import io.spring.uni_portal.model.TeachingScheduleRequest;
import io.spring.uni_portal.response.Response;
import io.spring.uni_portal.service.TeachingAssignmentService.ITeachingAssignmentService;
import io.spring.uni_portal.service.TeachingScheduleRequestService.ITeachingScheduleRequestService;
import io.spring.uni_portal.service.TeachingScheduleRequestService.TeachingScheduleRequestService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teaching-schedule")
public class TeachingScheduleController {

    @Autowired
    private TeachingScheduleRequestService teachingScheduleService;

    // API để lấy các lớp học đã phân công cho giảng viên
    @GetMapping("/assigned-classes")
    public Response<List<TeachingAssignmentResponseDTO>> getAssignedClasses(HttpServletRequest request) {
        return teachingScheduleService.getAssignedClasses(request);
    }

    // API để giảng viên đăng ký thời gian dạy cho lớp học
//    @PostMapping("/register")
//    public Response<String> registerTeachingSchedule(HttpServletRequest request,
//                                                     @RequestParam Long assignmentId,
//                                                     @RequestBody TeachingScheduleRequestDTO teachingScheduleRequestDTO) {
//        return teachingScheduleService.registerTeachingSchedule(request, assignmentId, teachingScheduleRequestDTO);
//    }
}
