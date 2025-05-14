package io.spring.uni_portal.controller;

import io.spring.uni_portal.dto.TeachingAssignment.TeachingAssignmentDetails;
import io.spring.uni_portal.dto.TeachingAssignment.TeachingAssignmentDetailsResponse;
import io.spring.uni_portal.dto.TeachingAssignment.TeachingAssignmentResponseDTO;
import io.spring.uni_portal.dto.TeachingScheduleRequest.TeachingScheduleRequestDTO;
import io.spring.uni_portal.dto.TeachingScheduleRequest.TeachingScheduleRequestResponseDTO;
import io.spring.uni_portal.dto.TeachingScheduleRequest.TeachingScheduleWithAssignmentResponseDTO;
import io.spring.uni_portal.model.TeachingScheduleRequest;
import io.spring.uni_portal.response.Response;
import io.spring.uni_portal.service.TeachingAssignmentService.ITeachingAssignmentService;
import io.spring.uni_portal.service.TeachingScheduleRequestService.ITeachingScheduleRequestService;
import io.spring.uni_portal.service.TeachingScheduleRequestService.TeachingScheduleRequestService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/teaching-schedule")
public class TeachingScheduleController {

    private final TeachingScheduleRequestService teachingScheduleRequestService;

    public TeachingScheduleController(TeachingScheduleRequestService teachingScheduleRequestService) {
        this.teachingScheduleRequestService = teachingScheduleRequestService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerTeachingSchedule(@RequestBody TeachingScheduleRequestDTO dto) {
        try {
            TeachingScheduleRequestResponseDTO savedRequest = teachingScheduleRequestService.registerTeachingSchedule(dto);
            return ResponseEntity.ok(savedRequest);
        } catch (IllegalArgumentException | IllegalStateException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Invalid request");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Server error");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(500).body(errorResponse);
        }
    }

    @PutMapping("/{assignmentId}")
    public ResponseEntity<TeachingScheduleRequestResponseDTO> updateTeachingSchedule(
            @PathVariable Long assignmentId,
            @RequestBody TeachingScheduleRequestDTO teachingScheduleRequestDTO) {
        teachingScheduleRequestDTO.setAssignmentId(assignmentId);  // Gán assignmentId từ URL vào DTO
        TeachingScheduleRequestResponseDTO responseDTO = teachingScheduleRequestService.updateTeachingSchedule(teachingScheduleRequestDTO);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping
    public Response<Page<TeachingScheduleWithAssignmentResponseDTO>> getAllTeachingSchedules(
            @RequestParam(value = "currentPage") int currentPage,
            @RequestParam(value = "perPage") int perPage,
            @RequestParam(value = "searchValue") String searchValue) {

        Pageable pageable = PageRequest.of(currentPage, perPage);
        Page<TeachingScheduleWithAssignmentResponseDTO> schedules = teachingScheduleRequestService.getAllTeachingSchedules(pageable, searchValue);
        return Response.success("Danh sách lịch giảng dạy", schedules);
    }


//    @PutMapping("/{scheduleId}")
//    public TeachingScheduleRequestResponseDTO updateTeachingSchedule(
//            @PathVariable Long scheduleId,
//            @RequestBody TeachingScheduleRequestDTO dto) {
//        return teachingScheduleRequestService.updateTeachingSchedule(scheduleId, dto);
//    }
}
