package io.spring.uni_portal.controller;

import io.spring.uni_portal.dto.TeachingAssignment.TeachingAssignmentDTO;
import io.spring.uni_portal.dto.TeachingAssignment.TeachingAssignmentDetailDTO;
import io.spring.uni_portal.dto.TeachingAssignment.TeachingAssignmentResponse;
import io.spring.uni_portal.dto.TeachingAssignment.TeachingAssignmentWithSchedulesDTO;
import io.spring.uni_portal.dto.TermClass.TermClassResponse;
import io.spring.uni_portal.dto.TermClass.TermClassResponseUnassignedClasses;
import io.spring.uni_portal.model.TeachingAssignment;
import io.spring.uni_portal.model.User;
import io.spring.uni_portal.repository.TeachingAssignmentRepository;
import io.spring.uni_portal.repository.TeachingScheduleRequestRepository;
import io.spring.uni_portal.response.Response;
import io.spring.uni_portal.service.TeachingAssignmentService.ITeachingAssignmentService;
import io.spring.uni_portal.service.TeachingAssignmentService.TeachingAssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teaching-assignment")
public class TeachingAssignmentController {

    @Autowired
    private ITeachingAssignmentService iteachingAssignmentService;

    @Autowired
    private TeachingScheduleRequestRepository scheduleRepository;

    @Autowired
    private TeachingAssignmentRepository teachingAssignmentRepository;


    // API phân chia lớp học cho giảng viên (POST)
    @PostMapping
    public ResponseEntity<Response<List<TeachingAssignmentResponse>>> assignClassToLecturer(@RequestBody TeachingAssignmentDTO dto) {
        Response<List<TeachingAssignmentResponse>> response = iteachingAssignmentService.assignClassToLecturer(dto);
        return ResponseEntity.ok(response);
    }

    // API lấy tất cả phân công giảng viên (GET)
    @GetMapping("/paging")
    public Response<Page<TeachingAssignmentResponse>> getAssignmentsPaging(
            @RequestParam int currentPage,
            @RequestParam int perPage,
            @RequestParam String searchValue
    ) {
        // Tạo Pageable từ các tham số truyền vào
        Pageable pageable = PageRequest.of(currentPage, perPage, Sort.by("termClass.classname").ascending());

        Response<Page<TeachingAssignmentResponse>> response = iteachingAssignmentService.getAssignmentsWithSearch(searchValue, pageable);

        Page<TeachingAssignmentResponse> result = response.getData();

        return Response.success("Danh sách phân công giảng dạy phân trang", result);
    }



    @GetMapping("/{id}")
    public ResponseEntity<Response<TeachingAssignmentDetailDTO>> getTeachingAssignmentDetail(@PathVariable("id") Long id) {
        Response<TeachingAssignmentDetailDTO> response = iteachingAssignmentService.getAssignmentById(id);
        return ResponseEntity.ok(response);
    }

    // API cập nhật phân công giảng viên (PUT)
    @PutMapping("/{id}")
    public ResponseEntity<Response<TeachingAssignmentResponse>> updateAssignment(@PathVariable Long id, @RequestBody TeachingAssignmentDTO dto) {
        Response<TeachingAssignmentResponse> response = iteachingAssignmentService.updateAssignment(id, dto);
        return ResponseEntity.ok(response);
    }

    // API xóa phân công giảng viên (DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<Response<Void>> deleteAssignment(@PathVariable Long id) {
        Response<Void> response = iteachingAssignmentService.deleteAssignment(id);
        return ResponseEntity.ok(response);
    }

    // API lấy tất cả lớp đã phân công giảng viên (GET)
    @GetMapping("/assigned-classes")
    public ResponseEntity<Response<List<TeachingAssignmentResponse>>> getAssignedClasses() {
        Response<List<TeachingAssignmentResponse>> response = iteachingAssignmentService.getAssignedClasses();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/unassigned-classes")
    public ResponseEntity<Response<List<TermClassResponseUnassignedClasses>>> getUnassignedClasses() {
        Response<List<TermClassResponseUnassignedClasses>> response = iteachingAssignmentService.getUnassignedClasses();
        return ResponseEntity.ok(response);
    }


    @GetMapping("/with-schedules/{assignmentId}")
    public ResponseEntity<Response<TeachingAssignmentWithSchedulesDTO>> getAssignmentWithSchedules(
            @PathVariable Long assignmentId) {
        TeachingAssignmentWithSchedulesDTO dto = iteachingAssignmentService.getAssignmentWithSchedules(assignmentId);
        return ResponseEntity.ok(Response.success("Lấy thông tin assignment và lịch giảng dạy thành công", dto));
    }


}
