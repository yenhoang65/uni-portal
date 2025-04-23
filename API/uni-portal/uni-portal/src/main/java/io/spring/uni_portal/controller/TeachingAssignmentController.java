package io.spring.uni_portal.controller;

import io.spring.uni_portal.dto.TeachingAssignment.TeachingAssignmentDTO;
import io.spring.uni_portal.dto.TeachingAssignment.TeachingAssignmentResponse;
import io.spring.uni_portal.model.TeachingAssignment;
import io.spring.uni_portal.response.Response;
import io.spring.uni_portal.service.TeachingAssignmentService.ITeachingAssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teaching-assignment")
public class TeachingAssignmentController {

    @Autowired
    private ITeachingAssignmentService service;

    @GetMapping
    public Response<List<TeachingAssignmentResponse>> getAll() {
        return Response.success("Lấy danh sách phân công thành công", service.getAll());
    }

    @PostMapping
    public Response<TeachingAssignmentResponse> create(@RequestBody TeachingAssignmentDTO dto) {
        TeachingAssignmentResponse result = service.create(dto);
        return Response.success("Tạo phân công thành công", result);
    }

    @PutMapping("/{id}")
    public Response<TeachingAssignment> update(@PathVariable Long id, @RequestBody TeachingAssignmentDTO dto) {
        return Response.success("Cập nhật phân công thành công", service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public Response<String> delete(@PathVariable Long id) {
        service.delete(id);
        return Response.success("Xóa phân công thành công", null);
    }

    @GetMapping("/lecturer/{userId}")
    public Response<List<TeachingAssignment>> getByLecturer(
            @PathVariable Long userId,
            @RequestParam Long schoolYear,
            @RequestParam Long semester
    ) {
        return Response.success("Lấy môn học đã phân cho giảng viên", service.getByLecturer(userId, schoolYear, semester));
    }
}
