package io.spring.uni_portal.controller;


import io.spring.uni_portal.dto.GradeType.GradeTypeRequestDTO;
import io.spring.uni_portal.dto.GradeType.GradeTypeResponseDTO;
import io.spring.uni_portal.response.Response;
import io.spring.uni_portal.service.GradeTypeService.IGradeTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/grade-types")
public class GradeTypeController {

    @Autowired
    private IGradeTypeService service;

    @PostMapping
    public Response<GradeTypeResponseDTO> create(@RequestBody GradeTypeRequestDTO dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    public Response<GradeTypeResponseDTO> update(@PathVariable Long id, @RequestBody GradeTypeRequestDTO dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public Response<String> delete(@PathVariable Long id) {
        return service.delete(id);
    }

    @GetMapping("/{id}")
    public Response<GradeTypeResponseDTO> getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping
    public Response<List<GradeTypeResponseDTO>> getAll() {
        return service.getAll();
    }
}
