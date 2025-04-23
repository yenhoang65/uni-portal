package io.spring.uni_portal.controller;

import io.spring.uni_portal.dto.Lecturer.LecturerRequestDTO;
import io.spring.uni_portal.dto.Lecturer.LecturerResponseDTO;
import io.spring.uni_portal.model.Lecturer;
import io.spring.uni_portal.response.Response;
import io.spring.uni_portal.service.LecturerService.ILecturerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/lecturers")
public class LecturerController {

    private final ILecturerService lecturerService;

    public LecturerController(ILecturerService lecturerService) {
        this.lecturerService = lecturerService;
    }

    @PostMapping
    public ResponseEntity<Response<LecturerResponseDTO>> createLecturer(@RequestBody LecturerRequestDTO request) {
        Lecturer createdLecturer = lecturerService.createLecturer(request);
        LecturerResponseDTO responseDTO = new LecturerResponseDTO(createdLecturer);
        return ResponseEntity.ok(Response.success("Tạo giảng viên thành công", responseDTO));
    }

    @PostMapping("/import")
    public ResponseEntity<Response<List<LecturerResponseDTO>>> importExcel(@RequestParam("file") MultipartFile file) {
        try {
            List<LecturerResponseDTO> result = lecturerService.importFromExcel(file);
            return ResponseEntity.ok(Response.success("Import giảng viên thành công", result));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Response.failure("Lỗi import: " + e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<Response<List<LecturerResponseDTO>>> getAllLecturers() {
        List<LecturerResponseDTO> lecturers = lecturerService.getAllLecturers();
        return ResponseEntity.ok(Response.success("Lấy danh sách giảng viên thành công", lecturers));
    }
    @GetMapping("/{userId}")
    public ResponseEntity<Response<LecturerResponseDTO>> getLecturerById(@PathVariable Long userId) {
        Lecturer lecturer = lecturerService.getLecturerById(userId);
        LecturerResponseDTO responseDTO = new LecturerResponseDTO(lecturer);
        return ResponseEntity.ok(Response.success("Lấy chi tiết giảng viên thành công", responseDTO));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Response<LecturerResponseDTO>> updateLecturer(@PathVariable Long userId, @RequestBody LecturerRequestDTO request) {
        Lecturer updatedLecturer = lecturerService.updateLecturer(userId, request);
        LecturerResponseDTO responseDTO = new LecturerResponseDTO(updatedLecturer);
        return ResponseEntity.ok(Response.success("Cập nhật giảng viên thành công", responseDTO));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Response<String>> deleteLecturer(@PathVariable Long userId) {
        lecturerService.deleteLecturer(userId);
        return ResponseEntity.ok(Response.success("Xóa giảng viên thành công", null));
    }
}
