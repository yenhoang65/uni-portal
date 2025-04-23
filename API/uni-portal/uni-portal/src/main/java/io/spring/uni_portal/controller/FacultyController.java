package io.spring.uni_portal.controller;

import io.spring.uni_portal.dto.Faculty.FacultyDTO;
import io.spring.uni_portal.response.Response;
import io.spring.uni_portal.service.FacultyService.IFacultyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/faculties")
public class FacultyController {

    @Autowired
    private IFacultyService facultyService;

    @PostMapping
    public ResponseEntity<Response<FacultyDTO>> createFaculty(@ModelAttribute FacultyDTO facultyDTO) {
        FacultyDTO result = facultyService.createFaculty(facultyDTO);
        return ResponseEntity.ok(Response.success("Tạo khoa thành công", result));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<FacultyDTO>> getById(@PathVariable Long id) {
        FacultyDTO result = facultyService.getFacultyById(id);
        return ResponseEntity.ok(Response.success("Lấy thông tin khoa thành công", result));
    }

    @GetMapping
    public ResponseEntity<Response<List<FacultyDTO>>> getAll() {
        List<FacultyDTO> list = facultyService.getAllFaculties();
        return ResponseEntity.ok(Response.success("Lấy danh sách khoa thành công", list));
    }

    @PostMapping("/{id}")
    public ResponseEntity<Response<FacultyDTO>> update(@PathVariable Long id, @ModelAttribute FacultyDTO dto) {
        FacultyDTO result = facultyService.updateFaculty(id, dto);
        return ResponseEntity.ok(Response.success("Cập nhật khoa thành công", result));
    }

    @GetMapping("/search")
    public ResponseEntity<Response<List<FacultyDTO>>> searchFacultyByName(@RequestParam String name) {
        List<FacultyDTO> result = facultyService.searchFacultiesByName(name);
        return ResponseEntity.ok(Response.success("Tìm kiếm khoa theo tên thành công", result));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<Void>> delete(@PathVariable Long id) {
        facultyService.deleteFaculty(id);
        return ResponseEntity.ok(Response.success("Xoá khoa thành công", null));
    }
}
