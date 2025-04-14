package io.spring.uni_portal.controller;

import io.spring.uni_portal.dto.Faculty.FacultyDTO;
import io.spring.uni_portal.response.Response;
import io.spring.uni_portal.service.FacultyService.IFacultyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/faculties")
public class FacultyController {

    @Autowired
    private IFacultyService facultyService;

    @PostMapping
    public Response<FacultyDTO> create(@RequestBody FacultyDTO dto) {
        FacultyDTO result = facultyService.createFaculty(dto);
        return Response.success("Tạo khoa thành công", result);
    }

    @GetMapping("/{id}")
    public Response<FacultyDTO> getById(@PathVariable Long id) {
        FacultyDTO result = facultyService.getFacultyById(id);
        return Response.success("Lấy thông tin khoa thành công", result);
    }

    @GetMapping
    public Response<List<FacultyDTO>> getAll() {
        List<FacultyDTO> list = facultyService.getAllFaculties();
        return Response.success("Lấy danh sách khoa thành công", list);
    }

    @PutMapping("/{id}")
    public Response<FacultyDTO> update(@PathVariable Long id, @RequestBody FacultyDTO dto) {
        FacultyDTO result = facultyService.updateFaculty(id, dto);
        return Response.success("Cập nhật khoa thành công", result);
    }

    @DeleteMapping("/{id}")
    public Response<Void> delete(@PathVariable Long id) {
        facultyService.deleteFaculty(id);
        return Response.success("Xoá khoa thành công", null);
    }
}
