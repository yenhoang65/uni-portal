package io.spring.uni_portal.controller;

import io.spring.uni_portal.dto.Major.MajorDTO;
import io.spring.uni_portal.dto.Specialization.SpecializationDTO;
import io.spring.uni_portal.dto.Specialization.SpecializationResponse;
import io.spring.uni_portal.response.Response;
import io.spring.uni_portal.service.SpecializationService.ISpecializationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/specializations")
public class SpecializationController {

    @Autowired
    private ISpecializationService specializationService;

    @PostMapping
    public Response<SpecializationResponse> create(@RequestBody SpecializationDTO dto) {
        return Response.success("Tạo chuyên ngành thành công", specializationService.create(dto));
    }

    @GetMapping("/{id}")
    public Response<SpecializationResponse> getById(@PathVariable Long id) {
        return Response.success("Lấy chuyên ngành thành công", specializationService.getById(id));
    }

    @GetMapping
    public Response<List<SpecializationResponse>> getAll() {
        return Response.success("Lấy danh sách chuyên ngành thành công", specializationService.getAll());
    }

    @PutMapping("/{id}")
    public Response<SpecializationResponse> update(@PathVariable Long id, @RequestBody SpecializationDTO dto) {
        return Response.success("Cập nhật chuyên ngành thành công", specializationService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public Response<SpecializationResponse> delete(@PathVariable Long id) {
        SpecializationResponse deleted = specializationService.getById(id);
        specializationService.delete(id);
        return Response.success("Xoá chuyên ngành thành công", deleted);
    }

    @GetMapping("/search")
    public Response<List<SpecializationResponse>> searchByName(@RequestParam String name) {
        return Response.success("Tìm kiếm chuyên ngành thành công", specializationService.searchByName(name));
    }

}
