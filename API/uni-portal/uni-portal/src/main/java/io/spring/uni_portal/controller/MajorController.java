package io.spring.uni_portal.controller;

import io.spring.uni_portal.dto.Major.MajorDTO;
import io.spring.uni_portal.response.Response;
import io.spring.uni_portal.service.MajorService.IMajorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/majors")
public class MajorController {

    @Autowired
    private IMajorService majorService;

    @PostMapping
    public Response<MajorDTO> create(@RequestBody MajorDTO dto) {
        return Response.success("Tạo ngành học thành công", majorService.createMajor(dto));
    }

    @GetMapping("/{id}")
    public Response<MajorDTO> getById(@PathVariable Long id) {
        return Response.success("Lấy ngành học thành công", majorService.getMajorById(id));
    }

    @GetMapping
    public Response<List<MajorDTO>> getAll() {
        return Response.success("Lấy danh sách ngành học thành công", majorService.getAllMajors());
    }

    @PutMapping("/{id}")
    public Response<MajorDTO> update(@PathVariable Long id, @RequestBody MajorDTO dto) {
        return Response.success("Cập nhật ngành học thành công", majorService.updateMajor(id, dto));
    }

    @DeleteMapping("/{id}")
    public Response<Void> delete(@PathVariable Long id) {
        majorService.deleteMajor(id);
        return Response.success("Xoá ngành học thành công", null);
    }
}
