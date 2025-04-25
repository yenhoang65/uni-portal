package io.spring.uni_portal.controller;

import io.spring.uni_portal.dto.TeachingAssignment.TeachingAssignmentDTO;
import io.spring.uni_portal.dto.TeachingAssignment.TeachingAssignmentResponse;
import io.spring.uni_portal.dto.TermClass.TermClassResponse;
import io.spring.uni_portal.dto.TermClass.TermClassResponseUnassignedClasses;
import io.spring.uni_portal.model.TeachingAssignment;
import io.spring.uni_portal.response.Response;
import io.spring.uni_portal.service.TeachingAssignmentService.ITeachingAssignmentService;
import io.spring.uni_portal.service.TeachingAssignmentService.TeachingAssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teaching-assignment")
public class TeachingAssignmentController {

    @Autowired
    private TeachingAssignmentService teachingAssignmentService;

    @Autowired
    private ITeachingAssignmentService iteachingAssignmentService;

    // API phân chia lớp học cho giảng viên (POST)
    @PostMapping
    public ResponseEntity<Response<TeachingAssignmentResponse>> assignClassToLecturer(@RequestBody TeachingAssignmentDTO dto) {
        Response<TeachingAssignmentResponse> response = iteachingAssignmentService.assignClassToLecturer(dto);
        return ResponseEntity.ok(response);
    }

    // API lấy tất cả phân công giảng viên (GET)
    @GetMapping
    public ResponseEntity<Response<List<TeachingAssignmentResponse>>> getAllAssignments() {
        Response<List<TeachingAssignmentResponse>> response = iteachingAssignmentService.getAllAssignments();
        return ResponseEntity.ok(response);
    }

    // API lấy phân công giảng viên dạy theo ID assignement (GET)
    @GetMapping("/{id}")
    public ResponseEntity<Response<TeachingAssignmentResponse>> getAssignmentById(@PathVariable Long id) {
        Response<TeachingAssignmentResponse> response = iteachingAssignmentService.getAssignmentById(id);
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
        // Gọi service để lấy danh sách các lớp chưa phân công giảng viên
        Response<List<TermClassResponseUnassignedClasses>> response = teachingAssignmentService.getUnassignedClasses();
        return ResponseEntity.ok(response);
    }

}
