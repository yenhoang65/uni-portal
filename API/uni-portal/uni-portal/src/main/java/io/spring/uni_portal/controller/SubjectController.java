package io.spring.uni_portal.controller;

import io.spring.uni_portal.dto.Subject.SubjectDTO;
import io.spring.uni_portal.dto.Subject.SubjectResponse;
import io.spring.uni_portal.response.Response;
import io.spring.uni_portal.service.SubjectService.ISubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subjects")
public class SubjectController {

    @Autowired
    private ISubjectService subjectService;

    @PostMapping
    public Response<SubjectResponse> create(@RequestBody SubjectDTO dto) {
        return Response.success("Tạo môn học thành công", subjectService.create(dto));
    }

    @GetMapping("/{id}")
    public Response<SubjectResponse> getById(@PathVariable Long id) {
        return Response.success("Lấy thông tin môn học thành công", subjectService.getById(id));
    }

    @GetMapping
    public Response<List<SubjectResponse>> getAll() {
        return Response.success("Lấy danh sách môn học thành công", subjectService.getAll());
    }

    @GetMapping("/search")
    public Response<List<SubjectResponse>> search(@RequestParam("keyword") String keyword) {
        return Response.success("Tìm kiếm môn học thành công", subjectService.searchByName(keyword));
    }

    @PutMapping("/{id}")
    public Response<SubjectResponse> update(@PathVariable Long id, @RequestBody SubjectDTO dto) {
        return Response.success("Cập nhật môn học thành công", subjectService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public Response<SubjectResponse> delete(@PathVariable Long id) {
        SubjectResponse deleted = subjectService.getById(id);
        subjectService.delete(id);
        return Response.success("Xoá môn học thành công", deleted);
    }
}
