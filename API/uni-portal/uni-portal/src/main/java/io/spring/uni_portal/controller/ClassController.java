package io.spring.uni_portal.controller;
import io.spring.uni_portal.dto.Class.ClassRequestDTO;
import io.spring.uni_portal.dto.Class.ClassResponseDTO;
import io.spring.uni_portal.response.Response;
import io.spring.uni_portal.service.ClassService.IClassService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/classes")
public class ClassController {

    private final IClassService classService;

    public ClassController(IClassService classService) {
        this.classService = classService;
    }

    @PostMapping
    public ResponseEntity<Response<ClassResponseDTO>> create(@RequestBody ClassRequestDTO dto) {
        return ResponseEntity.ok(Response.success("Tạo lớp thành công", classService.create(dto)));
    }

    @GetMapping
    public ResponseEntity<Response<List<ClassResponseDTO>>> getAll() {
        return ResponseEntity.ok(Response.success("Lấy danh sách lớp thành công", classService.getAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<ClassResponseDTO>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(Response.success("Lấy chi tiết lớp thành công", classService.getById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<ClassResponseDTO>> update(@PathVariable Long id, @RequestBody ClassRequestDTO dto) {
        return ResponseEntity.ok(Response.success("Cập nhật lớp thành công", classService.update(id, dto)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<String>> delete(@PathVariable Long id) {
        classService.delete(id);
        return ResponseEntity.ok(Response.success("Xóa lớp thành công", null));
    }

}
