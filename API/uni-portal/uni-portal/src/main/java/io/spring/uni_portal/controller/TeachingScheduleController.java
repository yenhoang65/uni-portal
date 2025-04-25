package io.spring.uni_portal.controller;

import io.spring.uni_portal.dto.TeachingAssignment.TeachingAssignmentDetails;
import io.spring.uni_portal.dto.TeachingAssignment.TeachingAssignmentDetailsResponse;
import io.spring.uni_portal.dto.TeachingScheduleRequest.TeachingScheduleRequestDTO;
import io.spring.uni_portal.model.TeachingScheduleRequest;
import io.spring.uni_portal.response.Response;
import io.spring.uni_portal.service.TeachingAssignmentService.ITeachingAssignmentService;
import io.spring.uni_portal.service.TeachingScheduleRequestService.ITeachingScheduleRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teaching-schedule")
public class TeachingScheduleController {
//
//    @Autowired
//    private ITeachingScheduleRequestService teachingScheduleRequestService;
//
//    @Autowired
//    private ITeachingAssignmentService teachingAssignmentService;
//
//    // API đăng ký lịch dạy cho giảng viên
//    @PostMapping
//    public ResponseEntity<Response<TeachingScheduleRequest>> createTeachingSchedule(@RequestBody TeachingScheduleRequestDTO dto) {
//        Response<TeachingScheduleRequest> response = teachingScheduleRequestService.createTeachingSchedule(dto);
//        return ResponseEntity.ok(response);
//    }
//
//    // API lấy danh sách lịch dạy theo phân công
//    @GetMapping("/{assignmentId}")
//    public ResponseEntity<Response<List<TeachingScheduleRequest>>> getTeachingSchedulesByAssignment(@PathVariable Long assignmentId) {
//        Response<List<TeachingScheduleRequest>> response = teachingScheduleRequestService.getTeachingSchedulesByAssignment(assignmentId);
//        return ResponseEntity.ok(response);
//    }
//
//    // API cập nhật lịch dạy
//    @PutMapping("/{scheduleId}")
//    public ResponseEntity<Response<TeachingScheduleRequest>> updateTeachingSchedule(@PathVariable Long scheduleId, @RequestBody TeachingScheduleRequestDTO dto) {
//        Response<TeachingScheduleRequest> response = teachingScheduleRequestService.updateTeachingSchedule(scheduleId, dto);
//        return ResponseEntity.ok(response);
//    }
//
//    // API xóa lịch dạy
//    @DeleteMapping("/{scheduleId}")
//    public ResponseEntity<Response<Void>> deleteTeachingSchedule(@PathVariable Long scheduleId) {
//        Response<Void> response = teachingScheduleRequestService.deleteTeachingSchedule(scheduleId);
//        return ResponseEntity.ok(response);
//    }
//
//    // API lấy danh sách phân công giảng viên của người dùng hiện tại (các lớp đã phân công)
////    @GetMapping("/assigned-classes")
////    public ResponseEntity<Response<List<TeachingAssignmentDetails>>> getAssignedClassesByCurrentUser() {
////        Response<List<TeachingAssignmentDetails>> response = teachingScheduleRequestService.getAssignmentsAndClassesByCurrentUser();
////        return ResponseEntity.ok(response);
////    }
}
